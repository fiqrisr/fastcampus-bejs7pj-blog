package com.fastcampus.blog.request.category;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class GetCategoriesRequest {
    private Integer page;
    private Integer limit;
}
