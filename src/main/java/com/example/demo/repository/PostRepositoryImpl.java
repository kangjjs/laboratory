package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.example.demo.entity.Post;
import com.example.demo.entity.QPost;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepositoryImpl implements CustomPostRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Post> findPostByPageImpl(Pageable pageable) {

		QPost post = QPost.post;

		List<Post> posts = queryFactory.selectFrom(post)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long total = queryFactory.select(post.count())
			.from(post)
			.fetchOne();

		if (total == null)
			throw new IllegalArgumentException("데이터가 없음");

		return new PageImpl<>(posts, pageable, total);
	}

	@Override
	public Page<Post> findPostByPageableExecutionUtils(Pageable pageable) {
		QPost post = QPost.post;

		List<Post> posts = queryFactory.selectFrom(post)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> total = queryFactory.select(post.count())
			.from(post);

		return PageableExecutionUtils.getPage(posts, pageable, total::fetchOne);
	}
}
