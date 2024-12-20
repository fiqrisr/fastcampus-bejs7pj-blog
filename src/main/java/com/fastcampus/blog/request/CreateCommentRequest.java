package com.fastcampus.blog.request;

import com.fastcampus.blog.entity.Post;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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
public class CreateCommentRequest {
    @Size(min = 2, max = 100, message = "name should be between 2 to 100 characters")
    @NotNull
    private String name;

    @Size(min = 2, max = 100)
    @Email
    @NotNull
    private String email;

    @NotNull
    @Size(min = 5, max = 1000, message = "body should be between 5 to 1000 characters")
    private String body;

    @NotNull
    private Post post;
}
