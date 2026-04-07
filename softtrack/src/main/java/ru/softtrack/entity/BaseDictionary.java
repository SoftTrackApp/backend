package ru.softtrack.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDictionary {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    
    /*@Override
    public int hashCode() {
        return Objects.hash(id);
    }*/
}
