package com.neeejm.posts.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neeejm.posts.exceptions.EmptyPostException;
import com.neeejm.posts.exceptions.PostNotFoundException;
import com.neeejm.posts.models.Post;
import com.neeejm.posts.repositories.PostRepository;
import com.neeejm.posts.services.PostService;

@Service
public class PostServiceImpl implements PostService {

    private static final String POST_NOT_FOUND_MSG = "Post with id '%s' not found";
    private static final String EMPTY_POST_MSG = "Post must have at least a title or content";

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post add(Post post) {
        throwIfEmptyPost(post);

        return postRepository.insert(post);
    }

    @Override
    public Post update(String postId, Post post) {
        throwIfEmptyPost(post);
        Post postToUpdate = findPostOrElseThrow(postId);

        postToUpdate.setContent(
            post.getContent() == null ? postToUpdate.getContent() : post.getContent()
        );
        postToUpdate.setTitle(
            post.getTitle() == null ? postToUpdate.getTitle() : post.getTitle()
        );
        postToUpdate.setModifiedAt(LocalDateTime.now());

        return postRepository.save(postToUpdate);
        
    }


    @Override
    public void delete(String postId) {
        postRepository.delete(findPostOrElseThrow(postId));
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post findById(String postId) {
        return findPostOrElseThrow(postId);
    }

    @Override
    public Post incrementViewsByOne(String postId) {
        Post post = findPostOrElseThrow(postId);
        post.setViews(post.getViews() + 1);

        return postRepository.save(post);
    }
    
    private void throwIfEmptyPost(Post post) {
        if (post.getTitle() == null && post.getContent() == null) {
            throw new EmptyPostException(EMPTY_POST_MSG);
        }
    }

    private Post findPostOrElseThrow(String postId) {
        return postRepository.findById(postId).orElseThrow(() ->
            new PostNotFoundException(POST_NOT_FOUND_MSG.formatted(postId))
        );
    }
}
