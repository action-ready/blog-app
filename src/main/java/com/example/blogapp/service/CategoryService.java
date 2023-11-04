package com.example.blogapp.service;

import com.example.blogapp.payload.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO getCategory(Long categoryId);

    List<CategoryDTO> getAllCategory();

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long  id);

    void deleteCategory(Long id);
}
