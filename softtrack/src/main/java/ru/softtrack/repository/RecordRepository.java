package ru.softtrack.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.softtrack.entity.Record;
import ru.softtrack.entity.UserEntity;

import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {

    Page<Record> findByCreatorAndReceiver(UserEntity creator, UserEntity receiver, Pageable pageable);
}
