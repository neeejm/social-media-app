package com.neeejm.posts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neeejm.posts.dtos.PostRequestDto;
import com.neeejm.posts.dtos.PostResponseDto;
import com.neeejm.posts.services.PostService;

@Controller
@RequestMapping(
    path = "posts",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(final PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return ResponseEntity.ok().body(postService.findAll());
    }

    @GetMapping("{postId}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable String postId) {
        return ResponseEntity.ok().body(postService.findById(postId));
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> addPost(@RequestBody PostRequestDto post) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.add(post));
    }

    @PutMapping("{postId}")
    public ResponseEntity<PostResponseDto> updatePost(
        @PathVariable String postId,
        @RequestBody PostRequestDto post
    ) {
        return ResponseEntity.ok().body(postService.update(postId, post));
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable String postId) {
        postService.delete(postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("{postId}")
    public ResponseEntity<PostResponseDto> incrementViewsByOne(@PathVariable String postId) {
        return ResponseEntity.ok().body(postService.incrementViewsByOne(postId));
    }
}
