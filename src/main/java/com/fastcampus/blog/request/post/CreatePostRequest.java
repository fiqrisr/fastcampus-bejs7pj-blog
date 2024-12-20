package com.fastcampus.blog.request.post;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class CreatePostRequest {
    @Size(min = 2, message = "minimal 2 characters")
    @NotNull(message = "title must not be null")
    private String title;

    @Size(min = 10, message = "minimal 10 characters")
    @NotNull(message = "body must not be null")
    private String body;

    @Size(min = 2, message = "minimal 2 characters")
    @NotNull(message = "slug must not be null")
    private String slug;
}
