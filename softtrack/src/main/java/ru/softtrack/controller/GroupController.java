package ru.softtrack.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.softtrack.dto.response.GroupResponse;
import ru.softtrack.dto.response.UserResponse;
import ru.softtrack.service.GroupService;
import ru.softtrack.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getAllGroups() {
        List<GroupResponse> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/{groupId}/users")
    public ResponseEntity<List<UserResponse>> getUsersByGroup(@PathVariable Integer groupId) {
        List<UserResponse> users = userService.getUsersByGroup(groupId);
        return ResponseEntity.ok(users);
    }

}
