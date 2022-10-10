package com.neeejm.posts.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        assertThat(capturedPost).isNotNull()
            .hasFieldOrPropertyWithValue("title", post.getTitle())
            .hasFieldOrPropertyWithValue("content", post.getContent())
            .hasFieldOrPropertyWithValue("views", post.getViews())
            .hasFieldOrPropertyWithValue("createdAt", post.getCreatedAt())
            .hasFieldOrPropertyWithValue("modifiedAt", post.getModifiedAt());

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
