package ru.softtrack.entity;

import java.sql.Timestamp;

public class Record {
    private String title;
    private String receiverId;
    private String creatorId;
    private int behaviorId;
    private String comment;
    private Timestamp createdAt;

    public Record(String title, String receiverId, String creatorId, int behaviorId, String comment, Timestamp createdAt) {
        this.title = title;
        this.receiverId = receiverId;
        this.creatorId = creatorId;
        this.behaviorId = behaviorId;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public int getBehaviorId() {
        return behaviorId;
    }

    public void setBehaviorId(int behaviorId) {
        this.behaviorId = behaviorId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    
}
