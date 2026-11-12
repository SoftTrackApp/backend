package ru.softtrack.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.softtrack.dto.LdapUserDto;
import ru.softtrack.dto.response.UserResponse;
import ru.softtrack.service.LdapUserService;
import ru.softtrack.service.UserService;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final LdapUserService ldapUserService;

    @GetMapping("/students")
    @PreAuthorize("hasAuthority('view_all_dashboards')")
    public List<UserResponse> getAllUsers() {
        List<LdapUserDto> students = ldapUserService.getAllStudents();
        if (students == null || students.isEmpty()) {
            return List.of();
        }
        students.sort(Comparator.comparing(LdapUserDto::sn)
                .thenComparing(LdapUserDto::givenName));
        return students.stream().map(user -> new UserResponse(user.uid(),user.givenName(),user.sn())).toList();
    }
}
