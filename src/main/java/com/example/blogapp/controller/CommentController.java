package com.example.blogapp.controller;


import com.example.blogapp.payload.CommentDTO;
import com.example.blogapp.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId,
                                                   @Valid @RequestBody CommentDTO commentDTO) {

        return new ResponseEntity<>(commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> getCommentByPostId(@PathVariable Long postId) {

        return commentService.getCommentByPostId(postId);
    }


    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long postId
            , @PathVariable Long commentId) {

        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long postId, @PathVariable Long commentId,
                                                    @Valid  @RequestBody CommentDTO commentDTO) {

        return ResponseEntity.ok(commentService.updateComment(postId,commentId,commentDTO));
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }
}
