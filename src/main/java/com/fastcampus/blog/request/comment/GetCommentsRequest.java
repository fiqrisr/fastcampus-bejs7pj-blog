package com.fastcampus.blog.request.comment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class GetCommentsRequest {
    @NotNull
    private String slug;
    private Integer page;
    private Integer limit;
}
