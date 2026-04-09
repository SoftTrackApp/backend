package ru.softtrack.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.softtrack.dto.request.RecordCreateRequest;
import ru.softtrack.dto.response.RecordResponse;
import ru.softtrack.entity.UserEntity;
import ru.softtrack.service.RecordService;
import ru.softtrack.service.UserService;
import ru.softtrack.entity.Record;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<RecordResponse> createRecord(
            @RequestBody RecordCreateRequest request,
            Authentication authentication) {

        String currentUser = authentication.getName();
        UserEntity creator = userService.findUserById(currentUser);

        UserEntity receiver = userService.findUserById(request.receiverId());

        Record record = recordService.createRecord(
                request.title(),
                creator,
                receiver,
                request.behaviorId(),
                request.comment()
        );

        RecordResponse response = new RecordResponse(
                record.getId(),
                record.getTitle(),
                record.getBehaviorId(),
                record.getComment(),
                record.getCreatedAt()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}
