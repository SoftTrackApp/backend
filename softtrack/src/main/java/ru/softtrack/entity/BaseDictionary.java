package ru.softtrack.entity;

import jakarta.persistence.*;
import java.util.Objects;

@MappedSuperclass
public abstract class BaseDictionary {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public BaseDictionary() {
    }

    public BaseDictionary(String name) {
        this.name = name;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
