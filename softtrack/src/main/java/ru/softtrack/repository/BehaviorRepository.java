package ru.softtrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.softtrack.entity.Behavior;

@Repository
public interface BehaviorRepository extends JpaRepository<Behavior,Integer> {
}
