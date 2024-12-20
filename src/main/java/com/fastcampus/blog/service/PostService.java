package com.fastcampus.blog.service;

import com.fastcampus.blog.entity.Post;
import com.fastcampus.blog.mapper.PostMapper;
import com.fastcampus.blog.repository.PostRepository;
import com.fastcampus.blog.request.CreatePostRequest;
import com.fastcampus.blog.resposne.CreatePostResponse;
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
        return postRepository.findFirstBySlugAndIsDeleted(slug, false).orElse(null);
    }

    public CreatePostResponse createPost(CreatePostRequest request) {
        Post post = PostMapper.INSTANCE.map(request);
        post.setCommentCount(0L);
        post.setCreatedAt(Instant.now().getEpochSecond());
        postRepository.save(post);

        return PostMapper.INSTANCE.map(post);
    }

    public Post updatePostBySlug(String slug, Post post) {
        Post savedPost = postRepository.findFirstBySlugAndIsDeleted(slug, false).orElse(null);

        if (savedPost == null)
            return null;

        post.setId(savedPost.getId());
        return postRepository.save(post);
    }

    public Boolean deletePostById(Integer id) {
        Post savedPost = postRepository.findById(id).orElse(null);

        if (savedPost == null)
            return false;

        savedPost.setDeleted(true);
        postRepository.save(savedPost);
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
