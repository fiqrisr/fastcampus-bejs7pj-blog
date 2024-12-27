package com.fastcampus.blog.repository;

import com.fastcampus.blog.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostRepositoryTests {
    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void beforeEach() {
        // Clear the post table
        postRepository.deleteAll();

        // Initialize test data
        Post post1 = new Post();
        post1.setSlug("first-post");
        post1.setDeleted(false);

        Post post2 = new Post();
        post2.setSlug("second-post");
        post2.setDeleted(true);

        Post post3 = new Post();
        post3.setSlug("third-post");
        post3.setDeleted(false);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
    }

    @Test
    void findFirstBySlug_slugExists_postReturned() {
        Optional<Post> post = postRepository.findFirstBySlug("first-post");
        assertThat(post).isPresent();
        assertThat(post.get().getSlug()).isEqualTo("first-post");
    }

    @Test
    void findFirstBySlug_slugDoesNotExist_noPostReturned() {
        Optional<Post> post = postRepository.findFirstBySlug("non-existent-post");
        assertThat(post).isNotPresent();
    }

    @Test
    void findFirstBySlugAndIsDeleted_slugAndDeletedFlagMatch_postReturned() {
        Optional<Post> post = postRepository.findFirstBySlugAndIsDeleted("second-post", true);
        assertThat(post).isPresent();
        assertThat(post.get().getSlug()).isEqualTo("second-post");
        assertThat(post.get().isDeleted()).isTrue();
    }

    @Test
    void findFirstBySlugAndIsDeleted_slugMatchesButDeletedFlagDoesNot_noPostReturned() {
        Optional<Post> post = postRepository.findFirstBySlugAndIsDeleted("second-post", false);
        assertThat(post).isNotPresent();
    }

    @Test
    void findByIsDeleted_deletedFlagFalse_postsReturned() {
        List<Post> posts = postRepository.findByIsDeleted(false);
        assertThat(posts).hasSize(2);
    }

    @Test
    void findByIsDeleted_deletedFlagTrue_postsReturned() {
        List<Post> posts = postRepository.findByIsDeleted(true);
        assertThat(posts).hasSize(1);
    }

    @Test
    void findByIsDeleted_noPostsWithGivenFlag_emptyListReturned() {
        postRepository.deleteAll();
        List<Post> posts = postRepository.findByIsDeleted(false);
        assertThat(posts).isEmpty();
    }

    @Test
    void findByIsDeletedWithPagination_deletedFlagFalseAndPagination_postsReturned() {
        PageRequest pageRequest = PageRequest.of(0, 1);
        List<Post> posts = postRepository.findByIsDeleted(false, pageRequest);
        assertThat(posts).hasSize(1);
    }

    @Test
    void findByIsDeletedWithPagination_deletedFlagTrueAndPagination_postsReturned() {
        PageRequest pageRequest = PageRequest.of(0, 1);
        List<Post> posts = postRepository.findByIsDeleted(true, pageRequest);
        assertThat(posts).hasSize(1);
    }

    @Test
    void findByIsDeletedWithPagination_noPosts_emptyListReturned() {
        postRepository.deleteAll();
        PageRequest pageRequest = PageRequest.of(0, 1);
        List<Post> posts = postRepository.findByIsDeleted(false, pageRequest);
        assertThat(posts).isEmpty();
    }
}
