package com.neeejm.posts.controllers;

import static org.junit.jupiter.api.Assertions.fail;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.neeejm.posts.dtos.PostRequestDto;
import com.neeejm.posts.dtos.PostResponseDto;
import com.neeejm.posts.dtos.converters.PostConverter;
import com.neeejm.posts.errors.ApiError;
import com.neeejm.posts.models.Post;
import com.neeejm.posts.services.PostService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerIntegrationTests {

    @Autowired
    private WebTestClient wClient;
    @Autowired
    private PostService postService;

    @Test
    void shouldAddPost() {
        // Given
        PostRequestDto post =
            PostRequestDto.builder()
                    .title("test")
                    .content("this is a test content")
                    .build();
        
        // When
        // Then
        wClient.post()
                .uri("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(post)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(PostResponseDto.class).value(v -> {
                    assertThat(v.getTitle()).isEqualTo(post.getTitle());
                    assertThat(v.getContent()).isEqualTo(post.getContent());
                });
    }

    @Test
    void shouldNotAddEmptyPost() {
        // Given
        PostRequestDto post = new PostRequestDto();
        
        // When
        // Then
        wClient.post()
                .uri("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(post)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ApiError.class).value(v ->
                    assertThat(v.getErrors()).containsOnly(
                        "Post must have at least a title or content"
                    )
                );
    }

    @Test
    void testDeletePost() {
        fail("should delete post not yet implemented");
    }

    @Test
    void shouldGetAllPosts() {
        // ... init converter
        PostConverter converter = new PostConverter();
        // Given
        Post post = Post.builder()
                        .title("test")
                        .content("this is a test content")
                        .build();
        post = postService.add(post);
        // ... Only keep time down to seconds cause json response does not contain nano seconds
        post.setCreatedAt(post.getCreatedAt().truncatedTo(ChronoUnit.SECONDS));
        post.setModifiedAt(post.getModifiedAt().truncatedTo(ChronoUnit.SECONDS));
            
        // When
        // Then
        wClient.get()
                .uri("/posts")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(PostResponseDto.class).hasSize(1).isEqualTo(
                    converter.convertEntityToResponseDto(List.of(post))
                );
    }

    @Test
    void testGetPostById() {
        fail("should get post by its id not yet implemented");
    }

    @Test
    void testIncrementViewsByOne() {
        fail("should increment post views by one not yet implemented");
    }

    @Test
    void testUpdatePost() {
        fail("should update post not yet implemented");
    }
}
