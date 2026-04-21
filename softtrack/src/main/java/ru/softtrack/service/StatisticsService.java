package ru.softtrack.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.softtrack.dto.response.BehaviorStatResponse;
import ru.softtrack.dto.response.SoftskillStatResponse;
import ru.softtrack.exception.AccessDeniedException;
import ru.softtrack.repository.RecordRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final RecordRepository recordRepository;

    public List<SoftskillStatResponse> getSoftskillStats(String requestedUserId, Authentication authentication) {
        validateAccess(requestedUserId, authentication);
        return recordRepository.countBySoftSkillForUser(requestedUserId);
    }

    public List<BehaviorStatResponse> getBehaviorStats(String requestedUserId, Integer softskillId, Authentication authentication) {
        validateAccess(requestedUserId, authentication);
        return recordRepository.countByBehaviorForSoftskill(requestedUserId, softskillId);
    }

    private void validateAccess(String requestedUserId, Authentication authentication) {
        boolean canViewAll = authentication.getAuthorities().stream().
                anyMatch(a -> a.getAuthority().equals("view_all_dashboards"));
        String currentUserId = authentication.getName();

        if(!canViewAll && !currentUserId.equals(requestedUserId)) {
            throw new AccessDeniedException();
        }
    }
}
