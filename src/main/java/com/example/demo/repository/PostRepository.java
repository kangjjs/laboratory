package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>, CustomPostRepository {
	@Query("SELECT p FROM Post p WHERE p.title = :title")
	List<Post> findByTitle(@Param("title") String title);
}
