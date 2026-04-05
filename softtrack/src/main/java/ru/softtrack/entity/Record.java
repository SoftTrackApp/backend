package ru.softtrack.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "records")
public class Record {
    
    @Id
    private Integer id;
    private String title;
    
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UserEntity receiver;
    
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private UserEntity creator;
    private Integer behaviorId;
    private String comment;
    private LocalDateTime createdAt;

    public Record() {
    }
    
    public Record(Integer id, String title, UserEntity receiver, UserEntity creator, Integer behaviorId, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.receiver = receiver;
        this.creator = creator;
        this.behaviorId = behaviorId;
        this.comment = comment;
        this.createdAt = createdAt;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(UserEntity receiver) {
        this.receiver = receiver;
    }

    public UserEntity getCreator() {
        return creator;
    }

    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }

    public Integer getBehaviorId() {
        return behaviorId;
    }

    public void setBehaviorId(Integer behaviorId) {
        this.behaviorId = behaviorId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    
}
