package com.fastcampus.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.fastcampus.blog.entity.Category;
import com.fastcampus.blog.request.category.CreateCategoryRequest;
import com.fastcampus.blog.response.category.CreateCategoryResponse;
import com.fastcampus.blog.response.category.GetCategoryResponse;
import com.fastcampus.blog.response.category.UpdateCategoryResponse;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category map(CreateCategoryRequest request);

    GetCategoryResponse mapToGetCategoryResponse(Category category);

    CreateCategoryResponse mapToCreateCategoryResponse(Category category);

    UpdateCategoryResponse mapToUpdateCategoryResponse(Category category);
}
