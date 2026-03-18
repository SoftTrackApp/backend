package ru.softtrack.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Service
public class JwtService {

    private final SecretKey key;
    @Getter
    @Setter
    private int duration = 1000*60*60;

    public JwtService(@Value("${jwt.secret}") @NonNull String jwtSecret) {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));;
    }

    public String generateToken(String login) {
        Date now = new Date(System.currentTimeMillis());
        Date expiry = new Date(System.currentTimeMillis() + duration);
        return Jwts.builder()
                .subject(login)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key).compact();
    }

    private Claims parseClaims(String token) {
          return  Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public boolean isValidToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException e) {
            log.debug("Токен невалиден: {}", e.getMessage());
            return false;
        }
    }

    public String extractUserId(String token) {
        return parseClaims(token).getSubject();
    }
}
