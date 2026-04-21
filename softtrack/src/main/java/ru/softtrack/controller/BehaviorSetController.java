package ru.softtrack.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.softtrack.dto.response.BehaviorSetResponse;
import ru.softtrack.service.BehaviorSetService;

import java.util.List;

@RestController
@RequestMapping("/behavior-sets")
@RequiredArgsConstructor
public class BehaviorSetController {

    private final BehaviorSetService behaviorSetService;

    @GetMapping
    @PreAuthorize("hasAuthority('create_record')")
    public ResponseEntity<List<BehaviorSetResponse>> getAllBehaviorSets() {
        List<BehaviorSetResponse> behaviorSets = behaviorSetService.getAllBehaviorSets();
        return ResponseEntity.ok(behaviorSets);
    }
}
