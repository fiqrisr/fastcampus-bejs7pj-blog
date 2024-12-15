package com.fastcampus.blog.controller;

import com.fastcampus.blog.entity.Post;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {
    @GetMapping("/")
    public List<Post> getPosts() {
        return List.of(new Post(), new Post());
    }
}
