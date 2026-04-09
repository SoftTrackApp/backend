package ru.softtrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.softtrack.entity.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {
}
