package com.neeejm.posts.dtos.converters;

import static org.assertj.core.api.Assertions.assertThat;

import com.neeejm.posts.dtos.PostRequestDto;
import com.neeejm.posts.dtos.PostResponseDto;
import com.neeejm.posts.models.Post;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostConverterTests {

  private PostConverter underTest;

  @BeforeEach
  void setup() {
    underTest = new PostConverter();
  }

  @Test
  void shouldConvertEntityToResponseDto() {
    // Given
    Post postToConvert = Post.builder().title("title 1").content("content 1").views(5).build();

    // When
    PostResponseDto expected = underTest.convertEntityToResponseDto(postToConvert);

    // Then
    assertThat(expected)
        .hasFieldOrPropertyWithValue("title", postToConvert.getTitle())
        .hasFieldOrPropertyWithValue("content", postToConvert.getContent())
        .hasFieldOrPropertyWithValue("views", postToConvert.getViews())
        .hasFieldOrPropertyWithValue("createdAt", postToConvert.getCreatedAt());
  }

  @Test
  void shouldConvertEntityListToResponseDtoList() {
    // Given
    List<Post> postsToConvert =
        List.of(
            Post.builder().title("title 1").content("content 1").views(505).build(),
            Post.builder().title("title 2").content("content 2").build());

    // When
    List<PostResponseDto> expected = underTest.convertEntityToResponseDto(postsToConvert);

    // Then
    assertThat(expected)
        .hasSize(2)
        .first()
        .hasFieldOrPropertyWithValue("title", postsToConvert.get(0).getTitle())
        .hasFieldOrPropertyWithValue("content", postsToConvert.get(0).getContent())
        .hasFieldOrPropertyWithValue("views", postsToConvert.get(0).getViews())
        .hasFieldOrPropertyWithValue("createdAt", postsToConvert.get(0).getCreatedAt());
  }

  @Test
  void shouldConvertRequestDtoToEntity() {
    // Given
    PostRequestDto postDtoToConvert =
        PostRequestDto.builder().title("title 1").content("content 1").build();

    // When
    Post expected = underTest.convertRequestDtoToEntity(postDtoToConvert);

    // Then
    assertThat(expected)
        .hasFieldOrPropertyWithValue("title", postDtoToConvert.getTitle())
        .hasFieldOrPropertyWithValue("content", postDtoToConvert.getContent());
  }
}
