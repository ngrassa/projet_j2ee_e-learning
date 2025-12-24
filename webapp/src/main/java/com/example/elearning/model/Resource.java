package com.example.elearning.model;

public class Resource {
    private final long id;
    private final long moduleId;
    private final String title;
    private final String path;
    private final String uploadedBy;

    public Resource(long id, long moduleId, String title, String path, String uploadedBy) {
        this.id = id;
        this.moduleId = moduleId;
        this.title = title;
        this.path = path;
        this.uploadedBy = uploadedBy;
    }

    public long getId() {
        return id;
    }

    public long getModuleId() {
        return moduleId;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }
}

