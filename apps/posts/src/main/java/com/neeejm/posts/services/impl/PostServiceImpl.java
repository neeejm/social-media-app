package com.neeejm.posts.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neeejm.posts.dtos.PostRequestDto;
import com.neeejm.posts.dtos.PostResponseDto;
import com.neeejm.posts.dtos.converters.PostConverter;
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
    private final PostConverter postConverter;

    @Autowired
    public PostServiceImpl(final PostRepository postRepository) {
        this.postRepository = postRepository;
        this.postConverter = new PostConverter();
    }

    @Override
    public PostResponseDto add(PostRequestDto post) {
        throwIfEmptyPost(post);

        return postConverter.convertEntityToResponseDto(
            postRepository.insert(
                postConverter.convertRequestDtoToEntity(post)
            )
        );
    }

    @Override
    public PostResponseDto update(String postId, PostRequestDto post) {
        throwIfEmptyPost(post);
        Post postToUpdate = findPostOrElseThrow(postId);

        postToUpdate.setContent(
            post.getContent() == null ? postToUpdate.getContent() : post.getContent()
        );
        postToUpdate.setTitle(
            post.getTitle() == null ? postToUpdate.getTitle() : post.getTitle()
        );
        postToUpdate.setModifiedAt(LocalDateTime.now());

        return postConverter.convertEntityToResponseDto(
            postRepository.save(postToUpdate)
        );
    }


    @Override
    public void delete(String postId) {
        postRepository.delete(findPostOrElseThrow(postId));
    }

    @Override
    public List<PostResponseDto> findAll() {
        return postConverter.convertEntityToResponseDto(
            postRepository.findAll()
        );
    }

    @Override
    public PostResponseDto findById(String postId) {
        return postConverter.convertEntityToResponseDto(
            findPostOrElseThrow(postId)
        );
    }

    @Override
    public PostResponseDto incrementViewsByOne(String postId) {
        Post post = findPostOrElseThrow(postId);
        post.setViews(post.getViews() + 1);

        return postConverter.convertEntityToResponseDto(
            postRepository.save(post)
        );
    }
    
    private void throwIfEmptyPost(PostRequestDto post) {
        if (post.getTitle() == null && post.getContent() == null) {
            throw new EmptyPostException(EMPTY_POST_MSG);
        }
    }

    private void throwIfPostNotFound(String postId) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException(POST_NOT_FOUND_MSG.formatted(postId));
        }
    }

    private Post findPostOrElseThrow(String postId) {
        return postRepository.findById(postId).orElseThrow(() ->
            new PostNotFoundException(POST_NOT_FOUND_MSG.formatted(postId))
        );
    }
}
