package com.example.blogapp.service.impl;


import com.example.blogapp.entity.Category;
import com.example.blogapp.entity.Post;
import com.example.blogapp.exception.ResourceNotfoundException;
import com.example.blogapp.payload.CategoryDTO;
import com.example.blogapp.payload.PostDTO;
import com.example.blogapp.payload.PostResponse;
import com.example.blogapp.repository.CategoryRepository;
import com.example.blogapp.repository.PostRepository;
import com.example.blogapp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public PostDTO createPost(PostDTO postDTO) {

        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotfoundException("Category", "id", postDTO.getCategoryId()));

        Post post = mapToEntity(postDTO);

        Post newPost = postRepository.save(post);
        post.setCategory(category);
        PostDTO postResponse = mapToDTO(newPost);

        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNO, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNO, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> listOfPosts = posts.getContent();

        List<PostDTO> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();

        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPage(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotfoundException("Post", "ID", id));
        return mapToDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long id) {
        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotfoundException("Category", "id", postDTO.getCategoryId()));
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotfoundException("Post", "ID", id));
        post.setTitle(postDTO.getTitle());
        post.setCategory(category);
        post.setContent(postDTO.getContent());
        post.setDescription(post.getDescription());
        Post updatePost = postRepository.save(post);
        return mapToDTO(updatePost);
    }

    @Override
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotfoundException("Post", "ID", id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDTO> getPostByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotfoundException("Category", "id", categoryId));

        List<Post> posts = postRepository.findByCategoryId(category.getId());

        return posts.stream().map((post) ->
                mapToDTO(post)).collect(Collectors.toList());
    }

    private PostDTO mapToDTO(Post post) {

        PostDTO postDTO = modelMapper.map(post, PostDTO.class);
//        PostDTO postDTO = new PostDTO();
//        postDTO.setId(post.getId());
//        postDTO.setTitle(post.getTitle());
//        postDTO.setDescription(post.getDescription());
//        postDTO.setContent(post.getContent());
        return postDTO;

    }

    private Post mapToEntity(PostDTO postDTO) {

        Post post = modelMapper.map(postDTO, Post.class);

//        Post post = new Post();
//        post.setId(postDTO.getId());
//        post.setTitle(postDTO.getTitle());
//        post.setDescription(postDTO.getDescription());
//        post.setContent(postDTO.getContent());
        return post;
    }
}
