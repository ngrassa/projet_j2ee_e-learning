package com.example.elearning.model;

public class User {
    private final long id;
    private final String username;
    private final String role;

    public User(long id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}

