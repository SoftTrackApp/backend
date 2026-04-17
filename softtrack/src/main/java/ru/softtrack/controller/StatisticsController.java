package ru.softtrack.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.softtrack.dto.response.BehaviorStatResponse;
import ru.softtrack.dto.response.SoftskillStatResponse;
import ru.softtrack.repository.RecordRepository;

import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final RecordRepository recordRepository;


    @GetMapping("/softskills")
    public ResponseEntity<List<SoftskillStatResponse>> getSoftskillStats(
            @RequestParam String userId) {

        List<SoftskillStatResponse> stats = recordRepository.countBySoftSkillForUser(userId);

        return ResponseEntity.ok(stats);
    }

    @GetMapping("/behaviors")
    public ResponseEntity<List<BehaviorStatResponse>> getBehaviorStats(
            @RequestParam String userId,
            @RequestParam Integer softskillId) {

        List<BehaviorStatResponse> stats = recordRepository.countByBehaviorForSoftskill(userId, softskillId);

        return ResponseEntity.ok(stats);
    }
}
