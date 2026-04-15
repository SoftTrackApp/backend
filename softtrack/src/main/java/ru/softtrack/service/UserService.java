package ru.softtrack.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.softtrack.dto.response.UserResponse;
import ru.softtrack.entity.Group;
import ru.softtrack.entity.UserEntity;
import ru.softtrack.exception.EntityNotFoundException;
import ru.softtrack.repository.GroupRepository;
import ru.softtrack.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public UserEntity findUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(UserEntity.class, id));
    }

    public List<UserResponse> getUsersByGroup(Integer groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException(Group.class, groupId));

        return group.getUsers().stream()
                .map(user -> new UserResponse(user.getId(), user.getFName(), user.getLName()))
                .collect(Collectors.toList());
    }
}
