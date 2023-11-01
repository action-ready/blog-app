package com.example.blogapp.service;

import com.example.blogapp.payload.PostDTO;
import com.example.blogapp.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO);
    PostResponse getAllPosts(int pageNO, int pageSize, String sortBy, String sortDir);

    PostDTO getPostById(Long id);

    PostDTO updatePost(PostDTO postDTO, Long id);

    void deletePostById(Long id);
}
