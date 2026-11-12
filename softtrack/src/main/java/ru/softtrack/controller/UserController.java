package ru.softtrack.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.softtrack.dto.response.UserResponse;
import ru.softtrack.service.GroupService;
import ru.softtrack.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final GroupService groupService;

    private final UserService userService;

    @GetMapping("/students")
    @PreAuthorize("hasAuthority('create_record')")
    public List<UserResponse> getStudents() {
        return userService.getAllStudents();
    }

}
