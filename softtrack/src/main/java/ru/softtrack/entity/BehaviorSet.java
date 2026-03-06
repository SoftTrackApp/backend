package ru.softtrack.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "behavior_sets")
public class BehaviorSet extends BaseDictionary{

    public BehaviorSet() {
    }

    public BehaviorSet(String name) {
        super(name);
    }
    
    @ManyToMany
    @JoinTable(
            name = "behavior_to_set",
            joinColumns = @JoinColumn(name = "set_id"),
            inverseJoinColumns = @JoinColumn(name = "behavior_id")
    )
    Set<Behavior> behaviors = new HashSet<>();

    public Set<Behavior> getBehaviors() {
        return behaviors;
    }

    public void setBehaviors(Set<Behavior> behaviors) {
        this.behaviors = behaviors;
    }
    
    
}
