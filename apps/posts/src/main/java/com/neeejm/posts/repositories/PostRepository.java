package com.neeejm.posts.repositories;

import com.neeejm.posts.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {}
