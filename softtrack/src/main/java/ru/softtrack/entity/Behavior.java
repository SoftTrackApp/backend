package ru.softtrack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "behaviors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Behavior extends BaseDictionary{

    @ManyToMany(mappedBy = "behaviors")
    Set<Softskill> softskills = new HashSet<>();
    
    @ManyToMany(mappedBy = "behaviors")
    Set<BehaviorSet> behaviorSets = new HashSet<>();

}
