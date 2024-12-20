package com.fastcampus.blog.service;

import com.fastcampus.blog.entity.Post;
import com.fastcampus.blog.exception.ApiException;
import com.fastcampus.blog.mapper.PostMapper;
import com.fastcampus.blog.repository.PostRepository;
import com.fastcampus.blog.request.post.CreatePostRequest;
import com.fastcampus.blog.request.post.GetPostsRequest;
import com.fastcampus.blog.request.post.UpdatePostBySlugRequest;
import com.fastcampus.blog.response.post.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PostService {
    @Autowired
    PostRepository postRepository;

    public List<GetPostResponse> getPosts(GetPostsRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit());
        List<Post> posts = postRepository.findByIsDeleted(false, pageRequest);
        List<GetPostResponse> response = new ArrayList<>();
        posts.forEach(post -> response.add(PostMapper.INSTANCE.mapToGetPostResponse(post)));
        return response;
    }

    public GetPostResponse getPostBySlug(String slug) {
        Post post = postRepository
                .findFirstBySlugAndIsDeleted(slug, false)
                .orElseThrow(() -> new ApiException("not found", HttpStatus.NOT_FOUND));

        return PostMapper.INSTANCE.mapToGetPostResponse(post);
    }

    public CreatePostResponse createPost(CreatePostRequest request) {
        Post post = PostMapper.INSTANCE.mapToCreatePostResponse(request);
        post.setCommentCount(0L);
        post.setCreatedAt(Instant.now().getEpochSecond());
        postRepository.save(post);

        return PostMapper.INSTANCE.mapToCreatePostResponse(post);
    }

    public UpdatePostBySlugResponse updatePostBySlug(String slug, UpdatePostBySlugRequest request) {
        Post savedPost = postRepository.findFirstBySlugAndIsDeleted(slug, false)
                .orElseThrow(() -> new ApiException("post not found", HttpStatus.NOT_FOUND));

        savedPost.setTitle(request.getTitle());
        savedPost.setBody(request.getBody());
        savedPost.setSlug(request.getSlug());
        postRepository.save(savedPost);

        return PostMapper.INSTANCE.mapToUpdatePostBySlugResponse(savedPost);
    }

    public DeletePostByIdResponse deletePostById(Integer id) {
        Post savedPost = postRepository.findById(id)
                .orElseThrow(() -> new ApiException("post not found", HttpStatus.NOT_FOUND));

        savedPost.setDeleted(true);
        postRepository.save(savedPost);

        return PostMapper.INSTANCE.mapToDeletePostByIdResponse(savedPost);
    }

    public PublishPostResponse publishPost(Integer id) {
        Post savedPost = postRepository.findById(id)
                .orElseThrow(() -> new ApiException("post not found", HttpStatus.NOT_FOUND));

        savedPost.setPublished(true);
        savedPost.setPublishedAt(Instant.now().getEpochSecond());
        postRepository.save(savedPost);

        return PostMapper.INSTANCE.mapToPublishPostResponse(savedPost);
    }
}
