package com.example.elearning.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try (Connection connection = DataSourceFactory.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute("""
                    CREATE TABLE IF NOT EXISTS users (
                        id IDENTITY PRIMARY KEY,
                        username VARCHAR(50) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        role VARCHAR(20) NOT NULL
                    )
                    """);

            statement.execute("""
                    CREATE TABLE IF NOT EXISTS modules (
                        id IDENTITY PRIMARY KEY,
                        title VARCHAR(100) NOT NULL,
                        description VARCHAR(255)
                    )
                    """);

            statement.execute("""
                    CREATE TABLE IF NOT EXISTS resources (
                        id IDENTITY PRIMARY KEY,
                        module_id BIGINT NOT NULL,
                        title VARCHAR(150) NOT NULL,
                        path VARCHAR(255) NOT NULL,
                        uploaded_by VARCHAR(50),
                        FOREIGN KEY (module_id) REFERENCES modules(id)
                    )
                    """);

            statement.execute("""
                    CREATE TABLE IF NOT EXISTS comments (
                        id IDENTITY PRIMARY KEY,
                        resource_id BIGINT NOT NULL,
                        author VARCHAR(50) NOT NULL,
                        content VARCHAR(500) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (resource_id) REFERENCES resources(id)
                    )
                    """);

            statement.execute("""
                    MERGE INTO users (username, password, role) KEY(username)
                    VALUES ('admin', 'admin123', 'ADMIN'),
                           ('student', 'student123', 'STUDENT')
                    """);

            statement.execute("""
                    MERGE INTO modules (id, title, description) KEY(id)
                    VALUES (1, 'Java Web', 'Servlets, JSP et JDBC'),
                           (2, 'DevOps', 'CI/CD et déploiement sur Tomcat')
                    """);

        } catch (SQLException e) {
            sce.getServletContext().log("Database initialization failed", e);
        }
    }
}

