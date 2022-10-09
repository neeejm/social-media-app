package com.neeejm.posts.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.neeejm.posts.models.Post;

public interface PostRepository extends MongoRepository<Post, String> {
    
}
