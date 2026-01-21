package com.example.demo.service;

import com.example.demo.entity.Post;
import com.example.demo.entity.PostStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PostResponse;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.PostWithoutIndexRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;
	private final PostWithoutIndexRepository postWithoutIndexRepository;

	public Page<PostResponse> findPosts(Pageable pageable) {
		return postRepository.findPostByPageableExecutionUtils(pageable)
			.map(PostResponse::of);
	}

	/**
	 * 기본 연산
	 */
	@Transactional
	public int expireNonBulk(LocalDateTime cutoff) {
		List<Post> targets = postRepository.findAllByCreatedAtBefore(cutoff);
		for (Post p : targets) {
			p.expire(); // flush 시 UPDATE N
		}
		log.info("[NonBulk] expire count={}", targets.size());
		return targets.size();
	}

	/**
	 * 벌크 연산
	 */
	@Transactional
	public int expireBulk(LocalDateTime cutoff) {
		int updated = postRepository.bulkExpire(PostStatus.EXPIRED, cutoff);
		log.info("[Bulk] updated={}", updated);
		return updated;
	}
}
