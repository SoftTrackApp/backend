package ru.softtrack.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.softtrack.dto.response.BehaviorResponse;
import ru.softtrack.dto.response.BehaviorSetResponse;
import ru.softtrack.repository.BehaviorSetRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BehaviorSetService {

    private final BehaviorSetRepository behaviorSetRepository;

    public List<BehaviorSetResponse> getAllBehaviorSets() {
        return behaviorSetRepository.findAll().stream()
                .map(set -> new BehaviorSetResponse(
                        set.getId(),
                        set.getName(),
                        set.getBehaviors().stream()
                                .map(b -> new BehaviorResponse(b.getId(), b.getName()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}
