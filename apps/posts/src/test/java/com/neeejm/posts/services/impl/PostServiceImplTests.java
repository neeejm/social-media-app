package com.neeejm.posts.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.neeejm.posts.exceptions.EmptyPostException;
import com.neeejm.posts.exceptions.PostNotFoundException;
import com.neeejm.posts.models.Post;
import com.neeejm.posts.repositories.PostRepository;
import com.neeejm.posts.services.PostService;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTests {

  private static final String POST_NOT_FOUND_MSG = "Post with id '%s' not found";
  private static final String EMPTY_POST_MSG = "Post must have at least a title or content";

  @Mock private PostRepository postRepository;
  @Captor private ArgumentCaptor<Post> postArgCaptor;

  private PostService underTest;
  private Post post;

  @BeforeEach
  void setup() {
    underTest = new PostServiceImpl(postRepository);
    post = Post.builder().title("test").content("this is a test content").build();
  }

  @Test
  void shouldAdd() {
    // When
    underTest.add(post);

    // Then
    then(postRepository).should().insert(postArgCaptor.capture());
    Post capturedPost = postArgCaptor.getValue();
    assertThat(capturedPost).isNotNull().isEqualTo(post);
  }

  @Test
  void shouldThrowOnAddWithEmptyPost() {
    // Given
    Post emtpyPost = new Post();

    // When
    Exception excpectedException = catchException(() -> underTest.add(emtpyPost));

    // Then
    assertThat(excpectedException)
        .isInstanceOf(EmptyPostException.class)
        .hasMessage(EMPTY_POST_MSG);
  }

  @Test
  void shouldUpdate() {
    // Given
    String postId = new ObjectId().toHexString();

    // ... Find a post with given id
    given(postRepository.findById(postId)).willReturn(Optional.of(post.toBuilder().build()));

    // When
    underTest.update(postId, post);

    // Then
    then(postRepository).should().save(postArgCaptor.capture());
    Post capturedPost = postArgCaptor.getValue();
    assertThat(capturedPost).isNotNull();
    assertThat(capturedPost.getTitle()).isEqualTo(post.getTitle());
    assertThat(capturedPost.getContent()).isEqualTo(post.getContent());
    assertThat(capturedPost.getCreatedAt()).isEqualTo(post.getCreatedAt());
    assertThat(capturedPost.getModifiedAt()).isAfter(post.getModifiedAt());
  }

  @Test
  void shouldThrowPostNotFoundOnUpdate() {
    // Given
    String postId = new ObjectId().toHexString();

    // When
    Exception excpectedException = catchException(() -> underTest.update(postId, post));

    // Then
    assertThat(excpectedException)
        .isInstanceOf(PostNotFoundException.class)
        .hasMessage(POST_NOT_FOUND_MSG.formatted(postId));
  }

  @Test
  void shouldThrowOnUpdateWithEmptyPost() {
    // Given
    Post post = new Post();
    String postId = new ObjectId().toHexString();

    // When
    Exception excpectedException = catchException(() -> underTest.update(postId, post));

    // Then
    assertThat(excpectedException)
        .isInstanceOf(EmptyPostException.class)
        .hasMessage(EMPTY_POST_MSG);
  }

  @Test
  void testIncrementViewsByOne() {
    // Given
    String postId = new ObjectId().toHexString();

    // ... Find a post with given id
    given(postRepository.findById(postId)).willReturn(Optional.of(post.toBuilder().build()));

    // When
    underTest.incrementViewsByOne(postId);

    // Then
    then(postRepository).should().save(postArgCaptor.capture());
    Post capturedPost = postArgCaptor.getValue();
    assertThat(capturedPost.getViews()).isEqualTo(post.getViews() + 1);
  }
}
