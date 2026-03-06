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
    private User receiver;
    
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;
    private Integer behaviorId;
    private String comment;
    private LocalDateTime createdAt;

    public Record() {
    }
    
    public Record(Integer id, String title, User receiver, User creator, Integer behaviorId, String comment, LocalDateTime createdAt) {
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

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
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
