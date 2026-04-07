package ru.softtrack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "behavior_sets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BehaviorSet extends BaseDictionary{

    @ManyToMany
    @JoinTable(
            name = "behavior_to_set",
            joinColumns = @JoinColumn(name = "set_id"),
            inverseJoinColumns = @JoinColumn(name = "behavior_id")
    )
    Set<Behavior> behaviors = new HashSet<>();
    
}
