package com.neeejm.posts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neeejm.posts.models.Post;
import com.neeejm.posts.repositories.PostRepository;

@Controller
@RequestMapping(
    path = "posts",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class PostController {

    private final PostRepository postRepository;

    @Autowired
    public PostController(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok().body(postRepository.findAll());
    }
}
