package com.neeejm.posts;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.neeejm.posts.models.Post;
import com.neeejm.posts.repositories.PostRepository;

@SpringBootApplication
public class PostsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostsApplication.class, args);
	}

	@Bean
	CommandLineRunner seed(PostRepository postRepository) {
		return args -> {
			Post post = Post.builder()
							.content("helllo")
							.build();
			postRepository.save(post);
		};
	}

}
