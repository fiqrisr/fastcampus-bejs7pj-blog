package com.fastcampus.blog.service;

import com.fastcampus.blog.entity.Post;
import com.fastcampus.blog.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PostService {
    @Autowired
    PostRepository postRepository;

    public Iterable<Post> getPosts() {
        return postRepository.findAll();
    }

    public Post getPostBySlug(String slug) {
        return postRepository.findFirstBySlug(slug).orElse(null);
    }

    public Post createPost(Post post) {
        post.setCreatedAt(Instant.now().getEpochSecond());
        return postRepository.save(post);
    }

    public Post updatePostBySlug(String slug, Post post) {
        Post savedPost = postRepository.findFirstBySlug(slug).orElse(null);

        if (savedPost == null)
            return null;

        post.setId(savedPost.getId());
        return postRepository.save(post);
    }

    public Boolean deletePostById(Integer id) {
        Post savedPost = postRepository.findById(id).orElse(null);

        if (savedPost == null)
            return false;

        postRepository.deleteById(id);
        return true;
    }

    public Post publishPost(Integer id) {
        Post savedPost = postRepository.findById(id).orElse(null);

        if (savedPost == null)
            return null;

        savedPost.setPublished(true);
        savedPost.setPublishedAt(Instant.now().getEpochSecond());
        return postRepository.save(savedPost);
    }
}
