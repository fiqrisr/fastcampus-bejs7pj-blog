package com.fastcampus.blog.controller;

import com.fastcampus.blog.request.post.CreatePostRequest;
import com.fastcampus.blog.request.post.GetPostsRequest;
import com.fastcampus.blog.request.post.UpdatePostBySlugRequest;
import com.fastcampus.blog.response.post.*;
import com.fastcampus.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping()
    public List<GetPostResponse> getPosts(@RequestParam(required = false, defaultValue = "0") Integer page,
                                          @RequestParam(required = false, defaultValue = "10") Integer limit) {
        GetPostsRequest request = GetPostsRequest.builder().page(page).limit(limit).build();
        return postService.getPosts(request);
    }

    @GetMapping("/{slug}")
    public GetPostResponse getPostBySlug(@PathVariable String slug) {
        return postService.getPostBySlug(slug);
    }

    @PostMapping()
    public CreatePostResponse createPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
        return postService.createPost(createPostRequest);
    }

    @PutMapping("/{slug}")
    public UpdatePostBySlugResponse updatePostBySlug(@PathVariable String slug, @Valid @RequestBody UpdatePostBySlugRequest request) {
        return postService.updatePostBySlug(slug, request);
    }

    @DeleteMapping("/{id}")
    public DeletePostByIdResponse deletePostById(@PathVariable Integer id) {
        return postService.deletePostById(id);
    }

    @PostMapping("/{id}/publish")
    public PublishPostResponse publishPost(@Valid @PathVariable Integer id) {
        return postService.publishPost(id);
    }
}
