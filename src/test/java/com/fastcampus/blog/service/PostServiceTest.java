package com.fastcampus.blog.service;

import com.fastcampus.blog.entity.Post;
import com.fastcampus.blog.mapper.PostMapper;
import com.fastcampus.blog.repository.PostRepository;
import com.fastcampus.blog.request.post.CreatePostRequest;
import com.fastcampus.blog.response.post.CreatePostResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class PostServiceTest {
    @Autowired
    @InjectMocks
    PostService postService;

    @Mock
    PostRepository postRepository;

    private AutoCloseable mocks;

    @BeforeEach
    void beforeEach() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach()
    void afterEach() throws Exception {
        mocks.close();
    }

    @Test
    void createPost_givenValid_shouldReturnOk() {
        CreatePostRequest postRequest = new CreatePostRequest();
        postRequest.setTitle("test post");
        postRequest.setSlug("test-post");
        postRequest.setBody("test body");

        CreatePostResponse postResponse = postService.createPost(postRequest);

        Post post = PostMapper.INSTANCE.mapToCreatePostResponse(postRequest);
        post.setCommentCount(0L);
        post.setCreatedAt(Instant.now().getEpochSecond());
        post.setId(1);

        when(postRepository.save(post)).thenReturn(post);

        Assertions.assertNotNull(postResponse);
        Assertions.assertEquals(0, postResponse.getCommentCount());
        Assertions.assertEquals("test-post", postResponse.getSlug());
    }
}
