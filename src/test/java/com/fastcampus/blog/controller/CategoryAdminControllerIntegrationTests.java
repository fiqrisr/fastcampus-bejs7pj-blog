package com.fastcampus.blog.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fastcampus.blog.service.JwtService;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryAdminControllerIntegrationTests {
    private final String AUTH_HEADER = "Authorization";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    JwtService jwtService;

    @Test
    void getCategories_givenValid_shouldReturnOk() throws Exception {
        String jwtToken = jwtService.generateTokenByUsername("fiqri");
        mockMvc.perform(get("/api/admin/categories")
                .header(this.AUTH_HEADER, "Bearer %s".formatted(jwtToken)))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }

    @Test
    void getCategoryById_givenValid_ShouldReturnOk() throws Exception {
        String jwtToken = jwtService.generateTokenByUsername("fiqri");
        mockMvc.perform(get("/api/admin/categories/1")
                .header(this.AUTH_HEADER, "Bearer %s".formatted(jwtToken)))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id": 1,
                            "name": "New Name",
                            "slug": "new-name"
                        }
                        """));
    }

    @Test
    void createCategory_givenValid_ShouldReturnCreated() throws Exception {
        String jwtToken = jwtService.generateTokenByUsername("fiqri");
        mockMvc.perform(post("/api/admin/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Tutorial",
                            "slug": "tutorial"
                        }
                        """)
                .header(this.AUTH_HEADER, "Bearer %s".formatted(jwtToken)))
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                            {
                                "id": 1,
                                "name": "Tutorial",
                                "slug": "tutorial"
                            }
                        """));
    }

    @Test
    void updateCategory_givenValid_ShouldReturnCreated() throws Exception {
        String jwtToken = jwtService.generateTokenByUsername("fiqri");
        mockMvc.perform(put("/api/admin/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "id": 1,
                            "name": "New Name",
                            "slug": "new-name"
                        }
                        """)
                .header(this.AUTH_HEADER, "Bearer %s".formatted(jwtToken)))
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                        {
                            "id": 1,
                            "name": "New Name",
                            "slug": "new-name"
                        }
                        """));
    }

    @Test
    void deleteCategoryById_givenValid_ShouldReturnOk() throws Exception {
        String jwtToken = jwtService.generateTokenByUsername("fiqri");
        mockMvc.perform(delete("/api/admin/categories/1")
                .header(this.AUTH_HEADER, "Bearer %s".formatted(jwtToken)))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id": 1,
                            "deleted": true
                        }
                        """));
    }
}
