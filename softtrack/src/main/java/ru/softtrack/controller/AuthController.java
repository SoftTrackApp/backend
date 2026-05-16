package ru.softtrack.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.softtrack.dto.request.SessionCreateRequest;
import ru.softtrack.dto.response.SessionResponse;
import ru.softtrack.entity.UserEntity;
import ru.softtrack.service.CookieService;
import ru.softtrack.service.JwtService;
import ru.softtrack.service.UserService;

@RestController
@RequestMapping("/session")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final CookieService cookieService;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    ResponseEntity<?> login(@Valid @RequestBody SessionCreateRequest request, HttpServletResponse response) throws ServletException {

        String id = request.getLogin();
        log.info("Login attempt for user: {}", id);
        String password = request.getPassword();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(id,password);

        Authentication authentication = authenticationManager.authenticate(authToken);

        UserEntity user = (UserEntity) authentication.getPrincipal();

        String token = jwtService.generateToken(user);

        cookieService.addSessionTokenToResponse(token,response);

        log.info("User logged in: {}", id);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @DeleteMapping
    ResponseEntity<?> logout(HttpServletResponse response) throws ServletException {
        cookieService.clearSessionToken(response);
        log.info("User logged out");
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<SessionResponse> getCurrentUser(Authentication authentication) {
        String id = authentication.getName();

        UserEntity user = userService.findUserById(id);

        SessionResponse response = new SessionResponse(
                user.getId(),
                user.getFName(),
                user.getLName(),
                user.getRole().getName()
        );

        return ResponseEntity.ok(response);
    }
}