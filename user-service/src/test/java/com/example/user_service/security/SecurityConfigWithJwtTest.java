package com.example.user_service.security;

import io.jsonwebtoken.*;
import com.example.user_service.auth.login.jwt.SecretPropertiesReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigWithJwtTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SecretPropertiesReader secretPropertiesReader;

    @Test
    void whenAccessProtectedEndpointWithValiJwt_thenSuccess() throws Exception {
        String jwtSecret = secretPropertiesReader.readSecretKey();
        String jwtToken = Jwts.builder()
                .setSubject("user")
                .claim("roles", "USER")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(3600)))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();

        mockMvc.perform(post("/user-service/auth/login")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }
}
