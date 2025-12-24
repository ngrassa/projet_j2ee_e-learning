package com.example.elearning.repository;

import com.example.elearning.config.DataSourceFactory;
import com.example.elearning.model.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentDao {

    public List<Comment> findByResource(long resourceId) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT id, resource_id, author, content, created_at FROM comments WHERE resource_id = ? ORDER BY created_at DESC";
        try (Connection connection = DataSourceFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, resourceId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    comments.add(new Comment(
                            rs.getLong("id"),
                            rs.getLong("resource_id"),
                            rs.getString("author"),
                            rs.getString("content"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    ));
                }
            }
        }
        return comments;
    }

    public void add(long resourceId, String author, String content) throws SQLException {
        String sql = "INSERT INTO comments (resource_id, author, content, created_at) VALUES (?, ?, ?, ?)";
        try (Connection connection = DataSourceFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, resourceId);
            ps.setString(2, author);
            ps.setString(3, content);
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();
        }
    }
}

