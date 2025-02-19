package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Post;

public interface CustomPostRepository {
	Page<Post> findPostByPageImpl(Pageable pageable);
	Page<Post> findPostByPageableExecutionUtils(Pageable pageable);
}
