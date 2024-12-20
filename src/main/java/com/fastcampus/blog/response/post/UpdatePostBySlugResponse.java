package com.fastcampus.blog.response.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostBySlugResponse {
    private Integer id;
    private String title;
    private String body;
    private String slug;
    private Long createdAt;
    private Long publishedAt;
    private Long commentCount;
    private boolean isPublished;
}
