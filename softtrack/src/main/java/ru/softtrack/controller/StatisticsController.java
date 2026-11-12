package ru.softtrack.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.softtrack.dto.response.BehaviorStatResponse;
import ru.softtrack.dto.response.SoftskillStatResponse;
import ru.softtrack.service.StatisticsService;

import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/softskills")
    public ResponseEntity<List<SoftskillStatResponse>> getSoftskillStats(
            @RequestParam String userId,
            Authentication authentication) {

        return ResponseEntity.ok(statisticsService.getSoftskillStats(userId,authentication));
    }

    @GetMapping("/behaviors")
    public ResponseEntity<List<BehaviorStatResponse>> getBehaviorStats(
            @RequestParam String userId,
            @RequestParam Integer softskillId,
            Authentication authentication) {

        return ResponseEntity.ok(statisticsService.getBehaviorStats(userId,softskillId,authentication));
    }
}