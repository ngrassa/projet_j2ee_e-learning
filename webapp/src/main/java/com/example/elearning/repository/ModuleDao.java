package com.example.elearning.repository;

import com.example.elearning.config.DataSourceFactory;
import com.example.elearning.model.CourseModule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModuleDao {

    public List<CourseModule> findAll() throws SQLException {
        List<CourseModule> modules = new ArrayList<>();
        String sql = "SELECT id, title, description FROM modules ORDER BY title";
        try (Connection connection = DataSourceFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                modules.add(new CourseModule(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description")
                ));
            }
        }
        return modules;
    }

    public CourseModule findById(long id) throws SQLException {
        String sql = "SELECT id, title, description FROM modules WHERE id = ?";
        try (Connection connection = DataSourceFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new CourseModule(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("description")
                    );
                }
            }
        }
        return null;
    }
}

