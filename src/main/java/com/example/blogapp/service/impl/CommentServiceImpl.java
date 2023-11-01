package com.example.blogapp.service.impl;


import com.example.blogapp.entity.Comment;
import com.example.blogapp.entity.Post;
import com.example.blogapp.exception.BlogAPIException;
import com.example.blogapp.exception.ResourceNotfoundException;
import com.example.blogapp.payload.CommentDTO;
import com.example.blogapp.repository.CommentRepository;
import com.example.blogapp.repository.PostRepository;
import com.example.blogapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {

        Comment comment = mapToEntity(commentDTO);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotfoundException("Post", "ID", postId));
        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);
        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDTO> getCommentByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotfoundException("Post", "ID", postId));


        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotfoundException("Comment", "ID", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return mapToDTO(comment);
    }

    @Override
    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotfoundException("Post", "ID", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotfoundException("Comment", "ID", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        comment.setName(commentDTO.getName());
        comment.setBody(commentDTO.getBody());
        comment.setEmail(commentDTO.getEmail());
        Comment updateComment = commentRepository.save(comment);
        return mapToDTO(updateComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotfoundException("Post", "ID", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotfoundException("Comment", "ID", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        commentRepository.delete(comment);
    }

    private CommentDTO mapToDTO(Comment comment) {
        CommentDTO commentDTO = modelMapper.map(comment,CommentDTO.class);
//
//        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setId(comment.getId());
//        commentDTO.setName(comment.getName());
//        commentDTO.setEmail(comment.getEmail());
//        commentDTO.setBody(comment.getBody());
        return commentDTO;
    }

    private Comment mapToEntity(CommentDTO commentDTO) {

        Comment comment = modelMapper.map(commentDTO, Comment.class);

//        Comment comment = new Comment();
//        comment.setId(commentDTO.getId());
//        comment.setName(commentDTO.getName());
//        comment.setEmail(commentDTO.getEmail());
//        comment.setBody(commentDTO.getBody());
        return comment;
    }
}
