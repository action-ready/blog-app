package com.example.blogapp.payload;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDTOV2 {
    private Long id;
    @NotEmpty
    @Size(min = 1, message = "Post title should have at least 2 characters")
    private String title;
    @NotEmpty
    @Size(min = 1, message = "Post description should have at least 10 characters")
    private String description;

    private Long categoryId;
    @NotEmpty
    private String content;
    private Set<CommentDTO> comments;
}
