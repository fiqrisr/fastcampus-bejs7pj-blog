package com.fastcampus.blog.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fastcampus.blog.entity.Category;
import com.fastcampus.blog.exception.ApiException;
import com.fastcampus.blog.mapper.CategoryMapper;
import com.fastcampus.blog.repository.CategoryRepository;
import com.fastcampus.blog.repository.PostRepository;
import com.fastcampus.blog.request.category.CreateCategoryRequest;
import com.fastcampus.blog.request.category.GetCategoriesRequest;
import com.fastcampus.blog.request.category.UpdateCategoryRequest;
import com.fastcampus.blog.response.category.CreateCategoryResponse;
import com.fastcampus.blog.response.category.DeleteCategoryResponse;
import com.fastcampus.blog.response.category.GetCategoryResponse;
import com.fastcampus.blog.response.category.UpdateCategoryResponse;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PostRepository postRepository;

    public List<GetCategoryResponse> getCategories(GetCategoriesRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit());
        List<Category> categories = categoryRepository.findByIsDeleted(false, pageRequest);
        List<GetCategoryResponse> response = new ArrayList<>();
        categories.forEach(category -> response.add(CategoryMapper.INSTANCE.mapToGetCategoryResponse(category)));
        return response;
    }

    public GetCategoryResponse getCategory(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (!category.isPresent()) {
            throw new ApiException("category not found", HttpStatus.NOT_FOUND);
        }

        return CategoryMapper.INSTANCE.mapToGetCategoryResponse(category.get());
    }

    public CreateCategoryResponse createCategory(CreateCategoryRequest request) {
        Category category = CategoryMapper.INSTANCE.map(request);
        category.setCreatedAt(Instant.now().getEpochSecond());
        categoryRepository.save(category);

        return CategoryMapper.INSTANCE.mapToCreateCategoryResponse(category);
    }

    public UpdateCategoryResponse updateCategory(Integer id, UpdateCategoryRequest request) {
        Optional<Category> category = categoryRepository.findById(id);

        if (!category.isPresent()) {
            throw new ApiException("category not found", HttpStatus.NOT_FOUND);
        }

        category.get().setName(request.getName());
        category.get().setSlug(request.getSlug());
        categoryRepository.save(category.get());

        return CategoryMapper.INSTANCE.mapToUpdateCategoryResponse(category.get());
    }

    public DeleteCategoryResponse deleteCategory(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        Long postCount = postRepository.countByCategory(category.get());

        if (postCount != 0) {
            throw new ApiException("post still exists in this category", HttpStatus.BAD_REQUEST);
        }

        category.get().setDeleted(true);
        categoryRepository.save(category.get());

        return DeleteCategoryResponse.builder().id(id).isDeleted(true).build();
    }

}
