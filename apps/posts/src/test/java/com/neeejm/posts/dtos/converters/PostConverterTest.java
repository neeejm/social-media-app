package com.neeejm.posts.dtos.converters;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.neeejm.posts.dtos.PostRequestDto;
import com.neeejm.posts.dtos.PostResponseDto;
import com.neeejm.posts.models.Post;

public class PostConverterTest {

    private PostConverter underTest;

    @BeforeEach
    void setup() {
        underTest = new PostConverter();
    }

    @Test
    void shouldConvertEntityToResponseDto() {
        Post postToConvert = Post.builder()
                                .title("title 1")
                                .content("content 1")
                                .views(5)
                                .build();
        
        PostResponseDto expected = underTest.convertEntityToResponseDto(postToConvert);

        assertThat(expected).hasFieldOrPropertyWithValue("title", postToConvert.getTitle())
                            .hasFieldOrPropertyWithValue("content", postToConvert.getContent())
                            .hasFieldOrPropertyWithValue("views", postToConvert.getViews())
                            .hasFieldOrPropertyWithValue("createdAt", postToConvert.getCreatedAt());
    }

    @Test
    void shouldConvertEntityListToResponseDtoList() {
        List<Post> postsToConvert = List.of(
            Post.builder()
                .title("title 1")
                .content("content 1")
                .views(505)
                .build(),
            Post.builder()
                .title("title 2")
                .content("content 2")
                .build()
        );
        
        List<PostResponseDto> expected = underTest.convertEntityToResponseDto(postsToConvert);

        assertThat(expected).hasSize(2).first()
                            .hasFieldOrPropertyWithValue(
                                "title", postsToConvert.get(0).getTitle())
                            .hasFieldOrPropertyWithValue(
                                "content", postsToConvert.get(0).getContent())
                            .hasFieldOrPropertyWithValue(
                                "views", postsToConvert.get(0).getViews())
                            .hasFieldOrPropertyWithValue(
                                "createdAt", postsToConvert.get(0).getCreatedAt());
    }

    @Test
    void shouldConvertRequestDtoToEntity() {
        PostRequestDto postDtoToConvert = PostRequestDto.builder()
                                                        .title("title 1")
                                                        .content("content 1")
                                                        .build();
        
        Post expected = underTest.convertRequestDtoToEntity(postDtoToConvert);

        assertThat(expected).hasFieldOrPropertyWithValue("title", postDtoToConvert.getTitle())
                            .hasFieldOrPropertyWithValue("content", postDtoToConvert.getContent());
    }
}
