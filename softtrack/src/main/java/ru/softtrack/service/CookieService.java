package ru.softtrack.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CookieService {
    public void addAccessTokenToResponse(String token, HttpServletResponse response) throws ServletException {
        Cookie cookie = new Cookie("access_token_cookie",token);

        cookie.setHttpOnly(true);
        //TODO (change to true later)
        cookie.setSecure(false);
        cookie.setPath("/");

        cookie.setMaxAge(60 * 60);
        cookie.setAttribute("SameSite", "Lax");
        response.addCookie(cookie);
    }

    public String getAccessTokenFromRequest(HttpServletRequest request) throws ServletException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("access_token_cookie".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public void clearAccessToken(HttpServletResponse response) throws ServletException{
        Cookie cookie = new Cookie("access_token_cookie", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}

