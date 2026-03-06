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
    Set<User> users = new HashSet<>();

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
    
    
}
