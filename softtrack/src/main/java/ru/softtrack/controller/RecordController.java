package ru.softtrack.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.softtrack.dto.request.RecordCreateRequest;
import ru.softtrack.dto.response.RecordResponse;
import ru.softtrack.entity.UserEntity;
import ru.softtrack.mapper.RecordMapper;
import ru.softtrack.repository.RecordRepository;
import ru.softtrack.service.RecordService;
import ru.softtrack.service.UserService;
import ru.softtrack.entity.Record;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;
    private final UserService userService;
    private final RecordRepository recordRepository;
    private final RecordMapper recordMapper;

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

        RecordResponse response = recordMapper.toResponse(record);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<?> getRecordsByReceiver(
            @RequestParam String receiverId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {

        UserEntity creator = userService.findUserById(authentication.getName());
        UserEntity receiver = userService.findUserById(receiverId);

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());

        Page<Record> recordsPage = recordRepository.findByCreatorAndReceiver(creator,receiver,pageable);
        Page<RecordResponse> responsePage = recordMapper.toResponsePage(recordsPage);

        return ResponseEntity.ok(responsePage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(
            @PathVariable Integer id,
            Authentication authentication) {

        UserEntity currentUser = userService.findUserById(authentication.getName());

        recordService.deleteRecord(id, currentUser);

        return ResponseEntity.noContent().build();
    }
}
