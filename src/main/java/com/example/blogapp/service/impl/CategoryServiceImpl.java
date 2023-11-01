package com.example.blogapp.service.impl;


import com.example.blogapp.entity.Category;
import com.example.blogapp.payload.CategoryDTO;
import com.example.blogapp.repository.CategoryRepository;
import com.example.blogapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        Category category = modelMapper.map(categoryDTO, Category.class);
        Category newCategory = categoryRepository.save(category);

        return modelMapper.map(newCategory, CategoryDTO.class);
    }
}
