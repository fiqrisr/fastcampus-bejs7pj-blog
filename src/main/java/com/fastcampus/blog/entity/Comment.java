package com.fastcampus.blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Comment {
    private Integer id;
    private Integer postId;
    private String name;
    private String email;
    private String body;
    private Long createdAt;
}
