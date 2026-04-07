package ru.softtrack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    private String fName;
    private String lName;
    
    @ManyToMany
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<Group> groups = new HashSet<>();

    //TODO delete OneToMany relationships?
    @OneToMany(mappedBy = "receiver")
    private Set<Record> receivedRecords = new HashSet<>();
    
    @OneToMany(mappedBy = "creator")
    private Set<Record> createdRecords = new HashSet<>();

    
}
