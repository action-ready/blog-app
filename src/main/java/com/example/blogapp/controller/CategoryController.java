package com.example.blogapp.controller;


import com.example.blogapp.payload.CategoryDTO;
import com.example.blogapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {


    private final CategoryService categoryService;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }


}
