package com.neeejm.posts.services;

import java.util.List;

import com.neeejm.posts.dtos.PostRequestDto;
import com.neeejm.posts.dtos.PostResponseDto;

public interface PostService {

    PostResponseDto add(PostRequestDto post);

    PostResponseDto update(String postId, PostRequestDto post);

    void delete(String postId);

    List<PostResponseDto> findAll();

    PostResponseDto findById(String postId);

    PostResponseDto incrementViewsByOne(String postId);
}
