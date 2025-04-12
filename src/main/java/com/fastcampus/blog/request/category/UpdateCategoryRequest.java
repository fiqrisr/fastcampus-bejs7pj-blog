package com.fastcampus.blog.request.category;

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
public class UpdateCategoryRequest {
    @Size(min = 2, message = "minimal 2 characters")
    @NotNull(message = "name must not be null")
    private String name;

    @Size(min = 2, message = "minimal 2 characters")
    @NotNull(message = "slug must not be null")
    private String slug;
}