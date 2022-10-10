package com.neeejm.posts.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neeejm.posts.exceptions.EmptyPostException;
import com.neeejm.posts.models.Post;
import com.neeejm.posts.repositories.PostRepository;
import com.neeejm.posts.services.PostService;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {

    private static final String POST_NOT_FOUND_MSG = "Post with id '%s' not found";
    private static final String EMPTY_POST_MSG = "Post must have at least a title or content";

    @Mock
    private PostRepository postRepository;
    @Captor
    private ArgumentCaptor<Post> postArgCaptor;

    private PostService underTest;

    @BeforeEach
    void setup() {
        underTest = new PostServiceImpl(postRepository);
    }

    @Test
    void shouldAdd() {
        // Given
        Post post = Post.builder()
                        .title("test")
                        .content("this is a test content")    
                        .build();

        // When
        underTest.add(post);

        // Then
        then(postRepository).should().insert(postArgCaptor.capture());
        Post capturedPost = postArgCaptor.getValue();
        assertThat(capturedPost).isNotNull().isEqualTo(post);
    }

    @Test
    void shouldThrowOnAdd() {
        // Given
        Post post = new Post();

        // When
        Exception excpectedException = catchException(() ->
            underTest.add(post)
        );

        // Then
        assertThat(excpectedException).isInstanceOf(EmptyPostException.class)
            .hasMessage(EMPTY_POST_MSG);
    }

    @Test
    void shouldUpdate() {
        // Given
        Post post = Post.builder()
                        .title("test")
                        .content("this is a test content")    
                        .build();
        String postId = new ObjectId().toHexString();

        // ... Find a post with given id
        given(postRepository.findById(postId)).willReturn(
            Optional.of(post.toBuilder().build())
        );

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
    void shouldThrowOnUpdate() {
        // Given
        Post post = new Post();
        String postId = new ObjectId().toHexString();

        // When
        Exception excpectedException = catchException(() ->
            underTest.update(postId, post)
        );

        // Then
        assertThat(excpectedException).isInstanceOf(EmptyPostException.class)
            .hasMessage(EMPTY_POST_MSG);
    }

    @Test
    void testDelete() {

    }

    @Test
    void testFindAll() {

    }

    @Test
    void testFindById() {

    }

    @Test
    void testIncrementViewsByOne() {

    }

    @Test
    void testUpdate() {

    }
}
