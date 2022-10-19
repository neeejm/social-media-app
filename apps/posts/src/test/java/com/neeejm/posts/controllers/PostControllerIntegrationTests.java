package com.neeejm.posts.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.neeejm.posts.dtos.PostRequestDto;
import com.neeejm.posts.dtos.PostResponseDto;
import com.neeejm.posts.dtos.converters.PostConverter;
import com.neeejm.posts.errors.ApiError;
import com.neeejm.posts.models.Post;
import com.neeejm.posts.services.PostService;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerIntegrationTests {

  private static final String POST_NOT_FOUND_MSG = "Post with id '%s' not found";
  private static final String EMPTY_POST_MSG = "Post must have at least a title or content";

  @Autowired private WebTestClient wClient;
  @Autowired private PostService postService;
  @Autowired private MongoTemplate mongoTemplate;

  @AfterEach
  void teardown() {
    mongoTemplate.dropCollection("post");
  }

  @Test
  void shouldAddPost() {
    // Given
    PostRequestDto post =
        PostRequestDto.builder().title("test").content("this is a test content").build();

    // When
    // Then
    wClient
        .post()
        .uri("/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(post)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody(PostResponseDto.class)
        .value(
            v -> {
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
    wClient
        .post()
        .uri("/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(post)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody(ApiError.class)
        .value(v -> assertThat(v.getErrors()).containsOnly(EMPTY_POST_MSG));
  }

  @Test
  void shouldGetAllPosts() {
    // ... init converter
    PostConverter converter = new PostConverter();
    // Given
    Post post = Post.builder().title("test").content("this is a test content").build();
    post = postService.add(post);
    // ... Only keep time down to seconds cause json response does not contain nano
    // seconds
    post.setCreatedAt(post.getCreatedAt().truncatedTo(ChronoUnit.SECONDS));
    post.setModifiedAt(post.getModifiedAt().truncatedTo(ChronoUnit.SECONDS));

    // When
    // Then
    wClient
        .get()
        .uri("/posts")
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBodyList(PostResponseDto.class)
        .hasSize(1)
        .isEqualTo(converter.convertEntityToResponseDto(List.of(post)));
  }

  @Test
  void shouldNotFindPost() {
    // Given
    String postId = new ObjectId().toHexString();

    // When
    // Then
    wClient
        .get()
        .uri("/posts/" + postId)
        .exchange()
        .expectStatus()
        .isNotFound()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody(ApiError.class)
        .value(v -> assertThat(v.getErrors()).containsOnly(POST_NOT_FOUND_MSG.formatted(postId)));
  }

  @Test
  void shouldGetPostById() {
    // ... init converter
    PostConverter converter = new PostConverter();
    // Given
    Post post = Post.builder().title("test").content("this is a test content").build();
    post = postService.add(post);
    // ... Only keep time down to seconds cause json response does not contain nano
    // seconds
    post.setCreatedAt(post.getCreatedAt().truncatedTo(ChronoUnit.SECONDS));
    post.setModifiedAt(post.getModifiedAt().truncatedTo(ChronoUnit.SECONDS));

    // When
    // Then
    wClient
        .get()
        .uri("/posts/" + post.getId())
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody(PostResponseDto.class)
        .isEqualTo(converter.convertEntityToResponseDto(post));
  }

  @Test
  void shouldIncrementViewsByOne() {
    // Given
    Post post = Post.builder().title("test").content("this is a test content").build();
    post = postService.add(post);
    int views = post.getViews();

    // When
    // Then
    wClient
        .patch()
        .uri("/posts/" + post.getId() + "/views")
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody(PostResponseDto.class)
        .value(v -> assertThat(v.getViews()).isEqualTo(views + 1));
  }

  @Test
  void testUpdatePost() {
    // Given
    Post post = Post.builder().title("test").content("this is a test content").build();
    post = postService.add(post);
    post.setTitle("test updated");

    // When
    // Then
    wClient
        .put()
        .uri("/posts/" + post.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(post)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody(PostResponseDto.class)
        .value(
            v -> {
              assertThat(v.getTitle()).isEqualTo("test updated");
            });
  }
}
