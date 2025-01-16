package com.fastcampus.blog.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
    }

    @AfterEach
    void afterEach() throws Exception {
    }

    @Test
    void login_givenValidRequest_shouldReturnOk() throws Exception {
        mockMvc.perform(post("/api/public/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "fiqri",
                                    "password": "abcd"
                                }
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token", startsWith("eyJ")));
    }

    @Test
    void login_givenInvalidRequest_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/public/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "username-1"
                                }
                                """)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                            "errorMessages": ["must not be null"]
                        }
                        """));

    }
}
