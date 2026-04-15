package ru.softtrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.softtrack.entity.BehaviorSet;

@Repository
public interface BehaviorSetRepository extends JpaRepository<BehaviorSet, Integer> {
}
