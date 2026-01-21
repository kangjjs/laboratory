package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.entity.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>, CustomPostRepository {
	@Query("SELECT p FROM Post p WHERE p.title = :title")
	List<Post> findByTitle(@Param("title") String title);

	List<Post> findAllByCreatedAtBefore(LocalDateTime cutoff);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("""
        update Post p
        set p.status = :status
        where p.createdAt < :cutoff
    """)
	int bulkExpire(PostStatus status, LocalDateTime cutoff);
}
