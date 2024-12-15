package com.fastcampus.blog.controller;

import com.fastcampus.blog.entity.Post;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class PostController {
    Post post1 = new Post(1, "post 1", "post-1");
    Post post2 = new Post(2, "post 2", "post-2");
    List<Post> posts = new ArrayList<Post>(Arrays.asList(post1, post2));

    @GetMapping("/")
    public List<Post> getPosts() {
        return posts;
    }

    @GetMapping("/{slug}")
    public Post getPostBySlug(@PathVariable String slug) {
        return posts.stream().filter(post -> post.getSlug().equals(slug)).findFirst().orElse(null);
    }

    @PostMapping("/")
    public Post createPost(@RequestBody Post post) {
        posts.add(post);
        return post;
    }

    @PutMapping("/{slug}")
    public Post updatePostBySlug(@PathVariable String slug, @RequestBody Post post) {
        Post savedPost = posts.stream().filter(p -> p.getSlug().equals(slug)).findFirst().orElse(null);

        if (savedPost == null)
            return null;

        savedPost.setTitle(post.getTitle());
        savedPost.setSlug(post.getSlug());
        return savedPost;
    }

    @DeleteMapping("/{id}")
    public Boolean deletePostById(@PathVariable Integer id) {
        Post savedPost = posts.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);

        if (savedPost == null)
            return false;

        posts.remove(savedPost);
        return true;
    }

    @PostMapping("/{id}/publish")
    public Post publishPost(@PathVariable Integer id) {
        Post savedPost = posts.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);

        if (savedPost == null)
            return null;

        savedPost.setPublished(true);
        return savedPost;
    }
}
