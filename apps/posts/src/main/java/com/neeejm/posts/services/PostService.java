package com.neeejm.posts.services;

import java.util.List;

import com.neeejm.posts.models.Post;

public interface PostService {

    Post add(Post post);

    Post update(String postId, Post post);

    void delete(String postId);

    List<Post> findAll();

    Post findById(String postId);

    Post incrementViewsByOne(String postId);
}
