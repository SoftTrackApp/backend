package ru.softtrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.softtrack.entity.UserEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {

    List<UserEntity> findByActiveTrue();

    List<UserEntity> findByActiveTrueAndIdNotIn(Collection<String> ids);

    Optional<UserEntity> findByIdAndActiveTrue(String id);
}
