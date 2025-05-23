package com.fastcampus.blog.controller;

import com.fastcampus.blog.entity.Comment;
import com.fastcampus.blog.entity.Post;
import com.fastcampus.blog.repository.CommentRepository;
import com.fastcampus.blog.repository.PostRepository;
import com.fastcampus.blog.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentAdminControllerIntegrationTests {
    private final String AUTH_HEADER = "Authorization";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    JwtService jwtService;

    @BeforeEach
    void beforeEach() {
        postRepository.deleteAll();
        commentRepository.deleteAll();

        Post post = new Post();
        post.setSlug("post-1");
        post.setTitle("Post 1");
        post.setCommentCount(1L);
        postRepository.save(post);

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setName("Fiqri");
        commentRepository.save(comment);
    }

    @Test
    void createComment_givenValid_shouldReturnCreated() throws Exception {
        String jwtToken = jwtService.generateTokenByUsername("fiqri");

        mockMvc.perform(get("/api/admin/comments/1")
                        .header(this.AUTH_HEADER, "Bearer %s".formatted(jwtToken))
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id": 1
                        }
                        """
                ));
    }
}
