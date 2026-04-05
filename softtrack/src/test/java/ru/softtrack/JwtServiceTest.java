package ru.softtrack;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import ru.softtrack.service.JwtService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtServiceTest {

    private JwtService jwtService;
    @Value("${jwt.secret}")
    private String secret;
    private int duration;
    private String user = "test";

    @BeforeEach
    void setUp(){
        jwtService = new JwtService(secret);
    }

    @Test
    void testTokenExpiry() {
        JwtService shortLived = new JwtService(secret);
        shortLived.setDuration(1);
        String token = shortLived.generateToken(user);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            
        }

        boolean isValid = shortLived.validateToken(token);

        assertFalse(isValid);
    }

    @Test
    void testExtractUserId() {
        String userToken = "";
        String token = jwtService.generateToken(user);
        assertNotNull(token);

        if (jwtService.validateToken(token)) {
            userToken = jwtService.extractUserId(token);
        }

        assertEquals(user,userToken);
    }


}
