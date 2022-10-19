package com.neeejm.posts.dtos.converters;

import com.neeejm.posts.dtos.PostRequestDto;
import com.neeejm.posts.dtos.PostResponseDto;
import com.neeejm.posts.models.Post;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

  private final ModelMapper mapper;

  public PostConverter() {
    this.mapper = new ModelMapper();
  }

  public Post convertRequestDtoToEntity(PostRequestDto postRequestDto) {
    return mapper.map(postRequestDto, Post.class);
  }

  public PostResponseDto convertEntityToResponseDto(Post post) {
    return mapper.map(post, PostResponseDto.class);
  }

  public List<PostResponseDto> convertEntityToResponseDto(List<Post> posts) {
    return posts.stream().map(post -> mapper.map(post, PostResponseDto.class)).toList();
  }
}
