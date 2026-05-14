package ru.softtrack.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

    @Value("${cookie.expiration:3600}")
    private int cookieMaxAge;

    public void addSessionTokenToResponse(String token, HttpServletResponse response) {
        Cookie cookie = new Cookie("session_token_cookie",token);

        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");

        cookie.setMaxAge(cookieMaxAge);
        cookie.setAttribute("SameSite", "None");
        response.addCookie(cookie);
    }

    public String getSessionTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("session_token_cookie".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public void clearSessionToken(HttpServletResponse response) {
        Cookie cookie = new Cookie("session_token_cookie", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}

