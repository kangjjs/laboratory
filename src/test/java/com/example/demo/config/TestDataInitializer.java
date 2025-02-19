package com.example.demo.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;

@Component
public class TestDataInitializer implements CommandLineRunner {

	@Autowired
	private final PostRepository postRepository;
	@Autowired
	private final UserRepository userRepository;

	public TestDataInitializer(PostRepository postRepository, UserRepository userRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}

	@Override
	public void run(String... args) {
		if (postRepository.count() == 0) {
			User user = userRepository.save(User.builder()
				.name("TestUser")
				.email("test@example.com")
				.password("password")
				.build());

			List<Post> posts = new ArrayList<>();
			for (int i = 1; i <= 10000; i++) {
				posts.add(Post.builder()
					.title("Test Post " + i)
					.content("This is a test post content " + i)
					.createdAt(LocalDateTime.now())
					.user(user)
					.build());
			}
			postRepository.saveAll(posts);

		}
	}

}
