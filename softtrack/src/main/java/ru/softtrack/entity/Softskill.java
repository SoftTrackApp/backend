package ru.softtrack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "softskills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Softskill extends BaseDictionary{

    @ManyToMany
    @JoinTable (
            name = "behavior_skill",
            joinColumns = @JoinColumn(name = "softskill_id"),
            inverseJoinColumns = @JoinColumn(name = "behavior_id")
    )
    Set<Behavior> behaviors = new HashSet<>();
}
