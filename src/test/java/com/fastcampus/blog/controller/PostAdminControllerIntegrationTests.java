package com.fastcampus.blog.controller;

import com.fastcampus.blog.entity.Post;
import com.fastcampus.blog.repository.PostRepository;
import com.fastcampus.blog.service.JwtService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostAdminControllerIntegrationTests {
    private final String AUTH_HEADER = "Authorization";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @Autowired
    JwtService jwtService;

    @BeforeEach
    void beforeEach() {
        postRepository.deleteAll();

        Post post = new Post();
        post.setSlug("slug-1");
        post.setTitle("title-1");
        post.setCommentCount(1L);
        postRepository.save(post);
    }

    @AfterEach
    void afterEach() {
        postRepository.deleteAll();
    }

    @Test
    void createPost_givenSlugIsAlreadyUsed_shouldReturnBadRequest() throws Exception {
        List<Post> postsBefore = (List<Post>) postRepository.findAll();

        String jwtToken = jwtService.generateTokenByUsername("fiqri");

        mockMvc.perform(post("/api/admin/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(this.AUTH_HEADER, "Bearer %s".formatted(jwtToken))
                        .content("""
                                {
                                    "slug": "slug-1",
                                    "title": "title-2",
                                    "body": "create post test"
                                }
                                """)
                )
                .andExpect(status().isBadRequest());

        List<Post> postsAfter = (List<Post>) postRepository.findAll();

        Assertions.assertEquals(postsBefore.size(), postsAfter.size());
    }
}
