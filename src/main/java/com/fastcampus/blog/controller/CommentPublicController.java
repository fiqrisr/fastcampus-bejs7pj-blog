package com.fastcampus.blog.controller;

import com.fastcampus.blog.request.comment.CreateCommentRequest;
import com.fastcampus.blog.request.comment.GetCommentsRequest;
import com.fastcampus.blog.response.comment.CreateCommentResponse;
import com.fastcampus.blog.response.comment.GetCommentResponse;
import com.fastcampus.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/comments")
public class CommentPublicController {
    @Autowired
    CommentService commentService;

    @GetMapping
    public List<GetCommentResponse> getComments(@RequestParam String postSlug,
                                                @RequestParam(required = false, defaultValue = "0") Integer page,
                                                @RequestParam(required = false, defaultValue = "10") Integer limit) {
        GetCommentsRequest request = GetCommentsRequest.builder().slug(postSlug).page(page).limit(limit).build();
        return commentService.getComments(request);
    }

    @PostMapping
    public ResponseEntity<CreateCommentResponse> createComment(@Valid @RequestBody CreateCommentRequest comment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(comment));
    }
}
