package ru.softtrack.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
public class User {
    @Id
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    private String fName;
    private String lName;

    public User() {
    }
    
    public User(String id, String fName, String lName) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
    }
    
    @ManyToMany
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<Group> groups = new HashSet<>();
    
    @OneToMany(mappedBy = "receiver")
    private Set<Record> receivedRecords = new HashSet<>();
    
    @OneToMany(mappedBy = "creator")
    private Set<Record> createdRecords = new HashSet<>();
    
    
    
    public String getLogin() {
        return id;
    }

    public void setLogin(String id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public Set<Record> getReceivedRecords() {
        return receivedRecords;
    }

    public void setReceivedRecords(Set<Record> receivedRecords) {
        this.receivedRecords = receivedRecords;
    }

    public Set<Record> getCreatedRecords() {
        return createdRecords;
    }

    public void setCreatedRecords(Set<Record> createdRecords) {
        this.createdRecords = createdRecords;
    }
    
}
