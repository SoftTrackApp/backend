package ru.softtrack.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.softtrack.entity.Permission;
import ru.softtrack.entity.UserEntity;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JwtService {

    private final SecretKey key;
    @Value("${jwt.expiration-ms:3600000}")
    private int duration;

    public JwtService(@Value("${jwt.secret}") @NonNull String jwtSecret) {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserEntity user) {
        Date now = new Date(System.currentTimeMillis());
        Date expiry = new Date(System.currentTimeMillis() + duration);
        Set<String> permissions = user.getRole().getPermissions()
                .stream().map(Permission::getName)
                .collect(Collectors.toSet());
        return Jwts.builder()
                .subject(user.getId())
                .claim("permissions",  permissions)
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

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException e) {
            log.debug("Invalid token: {}", e.getMessage());
            return false;
        }
    }

    public String extractUserId(String token) {
        return parseClaims(token).getSubject();
    }

}
