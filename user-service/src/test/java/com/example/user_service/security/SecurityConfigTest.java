package com.example.user_service.security;

import com.example.user_service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenAccessPublicLoginEndpoint_thenSuccess() throws Exception {
        String loginRequestJson = "{\"email\":\"test@example.com\",\"password\":\"password123\"}";
        mockMvc.perform(post("/user-service/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isOk());
    }

    @Test
    void whenAccessPublicRegisterEndpoint_thenSuccess() throws Exception {
        String userRequestJson = "{\"firstname\":\"John\",\"lastname\":\"Doe\",\"email\":\"test@example.com\",\"password\":\"password123\"}";
        mockMvc.perform(post("/user-service/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestJson))
                .andExpect(status().isOk());
    }
}
