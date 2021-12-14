package com.example.fixnow.models;

import java.util.Date;

public class serviceRequest {
    private Long userId;
    private Long technicianId;
    private Long created;
    private String description;

    public serviceRequest(Long userId, Long technicianId, Long created, String description) {
        this.userId = userId;
        this.technicianId = technicianId;
        this.created = created;
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(Long technicianId) {
        this.technicianId = technicianId;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
