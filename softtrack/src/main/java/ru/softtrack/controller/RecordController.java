package ru.softtrack.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.softtrack.dto.request.RecordCreateRequest;
import ru.softtrack.dto.response.RecordResponse;
import ru.softtrack.entity.UserEntity;
import ru.softtrack.mapper.RecordMapper;
import ru.softtrack.service.RecordService;
import ru.softtrack.service.UserService;
import ru.softtrack.entity.Record;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;
    private final UserService userService;
    private final RecordMapper recordMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('create_record')")
    public ResponseEntity<RecordResponse> createRecord(
            @Valid @RequestBody RecordCreateRequest request,
            Authentication authentication) {

        String currentUser = authentication.getName();
        UserEntity creator = userService.findUserById(currentUser);

        UserEntity receiver = userService.findOrCreateUser(request.receiverId());

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

    @GetMapping("/by-receiver/{receiverId}/mine")
    @PreAuthorize("hasAuthority('create_record')")
    public ResponseEntity<?> getMyRecordsForReceiver(
            @PathVariable("receiverId") String receiverId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {

        UserEntity creator = userService.findUserById(authentication.getName());
        UserEntity receiver = userService.findOrCreateUser(receiverId);

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());

        Page<RecordResponse> responsePage = recordService.getRecordsByCreatorAndReceiver(creator,receiver,pageable);

        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/by-receiver/{receiverId}")
    @PreAuthorize("hasAuthority('view_all_dashboards')")
    public ResponseEntity<?> getAllRecordsForReceiver(
            @PathVariable("receiverId") String receiverId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        UserEntity receiver = userService.findOrCreateUser(receiverId);

        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());

        Page<RecordResponse> responsePage = recordService.getRecordsByReceiver(receiver,pageable);

        return ResponseEntity.ok(responsePage);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('create_record')")
    public ResponseEntity<Void> deleteRecord(
            @PathVariable Integer id,
            Authentication authentication) {

        UserEntity currentUser = userService.findUserById(authentication.getName());

        recordService.deleteRecord(id, currentUser);

        return ResponseEntity.noContent().build();
    }
}