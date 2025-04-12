package com.fastcampus.blog.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fastcampus.blog.entity.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
    List<Category> findByIsDeleted(boolean isDeleted, PageRequest pageRequest);
}
