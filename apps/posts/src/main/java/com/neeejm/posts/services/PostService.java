package com.neeejm.posts.services;

import com.neeejm.posts.models.Post;
import java.util.List;

public interface PostService {

    Post add(Post post);

    Post update(String postId, Post post);

    void delete(String postId);

    List<Post> findAll();

    Post findById(String postId);

    Post incrementViewsByOne(String postId);
}
