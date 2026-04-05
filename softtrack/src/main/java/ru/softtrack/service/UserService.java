package ru.softtrack.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.softtrack.entity.UserEntity;
import ru.softtrack.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity findUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }
}
