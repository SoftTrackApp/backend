package ru.softtrack.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "behaviors")
public class Behavior extends BaseDictionary{

    public Behavior() {
    }

    public Behavior(String name) {
        super(name);
    }
    
    @ManyToMany(mappedBy = "behaviors")
    Set<Softskill> softskills = new HashSet<>();
    
    @ManyToMany(mappedBy = "behaviors")
    Set<BehaviorSet> behaviorSets = new HashSet<>();

    public Set<Softskill> getSoftskills() {
        return softskills;
    }

    public void setSoftskills(Set<Softskill> softskills) {
        this.softskills = softskills;
    }

    public Set<BehaviorSet> getBehaviorSets() {
        return behaviorSets;
    }

    public void setBehaviorSets(Set<BehaviorSet> behaviorSets) {
        this.behaviorSets = behaviorSets;
    }
    
    
}
