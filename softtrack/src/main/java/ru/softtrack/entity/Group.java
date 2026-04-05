package ru.softtrack.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="groups")
public class Group extends BaseDictionary{

    public Group() {
    }

    public Group(String name) {
        super(name);
    }
    
    @ManyToMany(mappedBy = "groups")
    Set<UserEntity> users = new HashSet<>();

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }
    
    
}
