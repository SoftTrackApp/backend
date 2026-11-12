package ru.softtrack.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.softtrack.dto.response.LdapGroupResponse;
import ru.softtrack.dto.response.UserResponse;
import ru.softtrack.service.GroupService;

import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

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
    public List<UserResponse> getIntersection( @RequestParam(name = "group", required = false) List<String> groups) {
        return groupService.getIntersection(groups);
    }
}