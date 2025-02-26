package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PostResponse;
import com.example.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;

	public Page<PostResponse> findPosts(Pageable pageable) {
		return postRepository.findPostByPageableExecutionUtils(pageable)
			.map(PostResponse::of);
	}
}
