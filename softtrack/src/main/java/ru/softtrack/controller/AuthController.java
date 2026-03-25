package ru.softtrack.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.softtrack.dto.SessionCreateRequest;
import ru.softtrack.dto.SessionResponse;
import ru.softtrack.entity.User;
import ru.softtrack.service.CookieService;
import ru.softtrack.service.JwtService;
import ru.softtrack.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final CookieService cookieService;

    @PostMapping
    ResponseEntity<?> login(@RequestBody SessionCreateRequest request, HttpServletResponse response) throws ServletException {

        String id = request.getLogin();
        String password = request.getPassword();

        User user;

        try {
            user = userService.findUserById(id);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid login or password"));
        }

        //TODO password check LDAP

        String token = jwtService.generateToken(user.getId());

        cookieService.addAccessTokenToResponse(token,response);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    ResponseEntity<?> logout(HttpServletResponse response) throws ServletException {
        cookieService.clearAccessToken(response);
        return ResponseEntity.ok().build();
    }

    //TODO get authorized user
}
