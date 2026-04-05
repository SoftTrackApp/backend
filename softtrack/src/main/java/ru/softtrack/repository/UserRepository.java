package ru.softtrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.softtrack.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {

}
