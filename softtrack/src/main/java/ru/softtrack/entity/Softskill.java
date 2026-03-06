package ru.softtrack.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "softskills")
public class Softskill extends BaseDictionary{

    public Softskill() {
    }

    public Softskill(String name) {
        super(name);
    }
    
    @ManyToMany
    @JoinTable (
            name = "behavior_skill",
            joinColumns = @JoinColumn(name = "softskill_id"),
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
