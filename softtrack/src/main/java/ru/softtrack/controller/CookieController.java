package ru.softtrack.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.softtrack.service.CookieService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/session")
public class CookieController {
    private final CookieService cookieService = new CookieService();

    @PostMapping
    public ResponseEntity<?> setCookie(@RequestParam String value, HttpServletResponse response) throws ServletException {
        cookieService.addAccessTokenToResponse(value, response);
        return ResponseEntity.ok(Map.of("message", "Cookie установлена", "value", value));
    }

    /*
    @GetMapping
    public ResponseEntity<?> getCookie(HttpServletRequest request) throws ServletException {
        String token = cookieService.getAccessTokenFromRequest(request);

        Map<String, Object> response = new HashMap<>();
        response.put("hasToken", token != null);
        response.put("token", token);

        return ResponseEntity.ok(response);
    }*/

    @DeleteMapping
    public ResponseEntity<?> clearCookie(HttpServletResponse response) throws ServletException {
        cookieService.clearAccessToken(response);
        return ResponseEntity.ok(Map.of("message", "Cookie удалена"));
    }
}
