package com.fastcampus.blog.controller;

import com.fastcampus.blog.exception.ApiException;
import com.fastcampus.blog.request.comment.GetCommentsRequest;
import com.fastcampus.blog.response.comment.GetCommentResponse;
import com.fastcampus.blog.service.CommentService;
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
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentPublicControllerTests {
    @Autowired
    @InjectMocks
    CommentPublicController commentPublicController;

    @Mock
    CommentService commentService;

    private AutoCloseable mocks;

    @BeforeEach
    void beforeEach() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void afterEach() throws Exception {
        mocks.close();
    }

    @Test
    void getComments_givenOneComment_shouldReturnOne() {
        GetCommentsRequest request = GetCommentsRequest
                .builder()
                .page(0)
                .limit(10)
                .slug("post-1")
                .build();

        GetCommentResponse getCommentResponse = new GetCommentResponse();
        getCommentResponse.setName("fiqri");
        getCommentResponse.setEmail("fiqri@test.com");
        getCommentResponse.setBody("comment body");
        getCommentResponse.setId(1);

        List<GetCommentResponse> actualCommentResponse =
                List.of(getCommentResponse);

        when(commentService.getComments(request)).thenReturn(actualCommentResponse);

        List<GetCommentResponse> commentResponses = commentPublicController
                .getComments("post-1", 0, 10);

        Assertions.assertNotNull(commentResponses);
        Assertions.assertEquals(1, commentResponses.size());
        Assertions.assertEquals(1, commentResponses.getFirst().getId());
        Assertions.assertEquals("comment body", commentResponses.getFirst().getBody());
    }

    @Test
    void getComments_givenPostInvalid_shouldThrowError() {
        GetCommentsRequest request = GetCommentsRequest
                .builder()
                .page(0)
                .limit(10)
                .slug("post-1")
                .build();

        when(commentService.getComments(request))
                .thenThrow(new ApiException("post not found", HttpStatus.NOT_FOUND));

        Assertions.assertThrows(ApiException.class, () -> commentPublicController
                .getComments("post-1", 0, 10));
    }

    @Test
    void getComments_givenNoComment_shouldReturnEmptyList() {
        GetCommentsRequest request = GetCommentsRequest
                .builder()
                .page(0)
                .limit(10)
                .slug("post-1")
                .build();

        List<GetCommentResponse> actualCommentResponse = List.of();

        when(commentService.getComments(request)).thenReturn(actualCommentResponse);

        List<GetCommentResponse> commentResponses = commentPublicController
                .getComments("post-1", 0, 10);

        Assertions.assertNotNull(commentResponses);
        Assertions.assertEquals(0, commentResponses.size());
    }
}
