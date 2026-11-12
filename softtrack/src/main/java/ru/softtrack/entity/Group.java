package ru.softtrack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group extends BaseDictionary{

    @ManyToMany(mappedBy = "groups")
    Set<UserEntity> users = new HashSet<>();
}