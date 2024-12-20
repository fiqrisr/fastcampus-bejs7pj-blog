package com.fastcampus.blog.response.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostResponse {
    private Integer id;
    private String title;
    private String body;
    private String slug;
    private Long publishedAt;
    private Long commentCount;
}
