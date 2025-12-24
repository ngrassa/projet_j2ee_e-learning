package com.example.elearning.model;

import java.time.LocalDateTime;

public class Comment {
    private final long id;
    private final long resourceId;
    private final String author;
    private final String content;
    private final LocalDateTime createdAt;

    public Comment(long id, long resourceId, String author, String content, LocalDateTime createdAt) {
        this.id = id;
        this.resourceId = resourceId;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public long getResourceId() {
        return resourceId;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

