package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.entity.Post;

import lombok.Builder;

@Builder
public record PostResponse(
	Long id,
	String title,
	String content,
	LocalDateTime createdAt,
	String userName
) {
	public static PostResponse of(Post post) {
		return PostResponse.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.createdAt(post.getCreatedAt())
			.userName(post.getUser().getName())
			.build();
	}
}
