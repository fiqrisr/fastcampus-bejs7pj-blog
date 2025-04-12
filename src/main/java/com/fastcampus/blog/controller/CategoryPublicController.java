package com.fastcampus.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.blog.request.category.GetCategoriesRequest;
import com.fastcampus.blog.response.category.GetCategoryResponse;
import com.fastcampus.blog.service.CategoryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/public/categories")
public class CategoryPublicController {

    @Autowired
    CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<GetCategoryResponse>> getCategories(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int limit) {
        GetCategoriesRequest request = GetCategoriesRequest.builder()
                .page(page)
                .limit(limit)
                .build();
        List<GetCategoryResponse> responses = categoryService.getCategories(request);
        return ResponseEntity.ok(responses);
    }

}
