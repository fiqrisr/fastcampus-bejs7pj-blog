package com.fastcampus.blog.controller;

import com.fastcampus.blog.request.post.GetPostsRequest;
import com.fastcampus.blog.response.post.GetPostResponse;
import com.fastcampus.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/posts")
public class PostPublicController {
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
}
