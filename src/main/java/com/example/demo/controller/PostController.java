package com.example.demo.controller;

import com.example.demo.dto.PostResponse;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@GetMapping("/posts")
	public Page<PostResponse> getPosts(@PageableDefault Pageable pageable) {
		log.info("PostService의 실제 클래스: {}", postService.getClass());
		log.info("PostService의 원본 클래스: {}", AopProxyUtils.ultimateTargetClass(postService));

		return postService.findPosts(pageable);
	}

	@PostMapping("/expire/non-bulk")
	public int nonBulk(@RequestParam int daysAgo) {
		LocalDateTime cutoff = LocalDateTime.now().minusDays(daysAgo);
		return postService.expireNonBulk(cutoff);
	}

	@PostMapping("/expire/bulk")
	public int bulk(@RequestParam int daysAgo) {
		LocalDateTime cutoff = LocalDateTime.now().minusDays(daysAgo);
		return postService.expireBulk(cutoff);
	}
}

