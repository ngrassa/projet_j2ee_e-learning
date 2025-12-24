package com.example.elearning.repository;

import com.example.elearning.config.DataSourceFactory;
import com.example.elearning.model.Resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResourceDao {

    public List<Resource> findByModule(long moduleId) throws SQLException {
        List<Resource> resources = new ArrayList<>();
        String sql = "SELECT id, module_id, title, path, uploaded_by FROM resources WHERE module_id = ? ORDER BY id DESC";
        try (Connection connection = DataSourceFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, moduleId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resources.add(mapRow(rs));
                }
            }
        }
        return resources;
    }

    public Resource findById(long id) throws SQLException {
        String sql = "SELECT id, module_id, title, path, uploaded_by FROM resources WHERE id = ?";
        try (Connection connection = DataSourceFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public void add(long moduleId, String title, String path, String uploadedBy) throws SQLException {
        String sql = "INSERT INTO resources (module_id, title, path, uploaded_by) VALUES (?, ?, ?, ?)";
        try (Connection connection = DataSourceFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, moduleId);
            ps.setString(2, title);
            ps.setString(3, path);
            ps.setString(4, uploadedBy);
            ps.executeUpdate();
        }
    }

    private Resource mapRow(ResultSet rs) throws SQLException {
        return new Resource(
                rs.getLong("id"),
                rs.getLong("module_id"),
                rs.getString("title"),
                rs.getString("path"),
                rs.getString("uploaded_by")
        );
    }
}

