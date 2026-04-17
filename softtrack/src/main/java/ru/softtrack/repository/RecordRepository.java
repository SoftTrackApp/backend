package ru.softtrack.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.softtrack.dto.response.BehaviorStatResponse;
import ru.softtrack.dto.response.SoftskillStatResponse;
import ru.softtrack.entity.Record;
import ru.softtrack.entity.UserEntity;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {

    Page<Record> findByCreatorAndReceiver(UserEntity creator, UserEntity receiver, Pageable pageable);

    @Query("SELECT s.id, s.name, COUNT(r) FROM Record r " +
            "JOIN r.behavior b " +
            "JOIN b.softskills s " +
            "WHERE r.receiver.id = :userId " +
            "GROUP BY s.id, s.name ORDER BY COUNT(r) DESC")
    List<SoftskillStatResponse> countBySoftSkillForUser(@Param("userId") String userId);

    @Query("SELECT b.id, b.name, COUNT(r) FROM Record r " +
            "JOIN r.behavior b " +
            "JOIN b.softskills s " +
            "WHERE r.receiver.id = :userId " +
            "AND s.id = :softskillId " +
            "GROUP BY b.id, b.name ORDER BY COUNT(r) DESC")
    List<BehaviorStatResponse> countByBehaviorForSoftskill(
            @Param("userId") String userId,
            @Param("softskillId") Integer softskillId
    );
}
