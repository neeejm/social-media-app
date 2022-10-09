package com.neeejm.posts.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.neeejm.posts.models.Post;

public interface PostRepository extends MongoRepository<Post, String> {
    
}
