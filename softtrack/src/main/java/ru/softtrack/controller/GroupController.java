package ru.softtrack.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.softtrack.dto.request.GroupIntersectionRequest;
import ru.softtrack.dto.response.GroupResponse;
import ru.softtrack.dto.response.LdapGroupResponse;
import ru.softtrack.dto.response.UserResponse;
import ru.softtrack.service.GroupService;
import ru.softtrack.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

//    @GetMapping
//    @PreAuthorize("hasAuthority('create_record')")
//    public ResponseEntity<List<GroupResponse>> getAllGroups() {
//        List<GroupResponse> groups = groupService.getAllGroups();
//        return ResponseEntity.ok(groups);
//    }
//
//    @GetMapping("/{groupId}/users")
//    @PreAuthorize("hasAuthority('create_record')")
//    public ResponseEntity<List<UserResponse>> getUsersByGroup(@PathVariable Integer groupId) {
//        List<UserResponse> users = userService.getUsersByGroup(groupId);
//        return ResponseEntity.ok(users);
//    }

    @GetMapping("/academic")
    @PreAuthorize("hasAuthority('create_record')")
    public List<LdapGroupResponse> getAcademicGroups() {
        return groupService.getAcademicGroups();
    }

    @GetMapping("/other")
    @PreAuthorize("hasAuthority('create_record')")
    public List<LdapGroupResponse> getOtherGroups() {
        return groupService.getOtherGroups();
    }

    @GetMapping("/intersection")
    @PreAuthorize("hasAuthority('create_record')")
    public List<UserResponse> getIntersection(
        @RequestParam(required = false) String group,
        @RequestParam(required = false) String subgroup
    ) {
        return groupService.getIntersection(group, subgroup);
    }
}