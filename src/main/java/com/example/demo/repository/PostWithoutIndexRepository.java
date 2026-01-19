package com.example.demo.repository;

import com.example.demo.entity.PostWithoutIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostWithoutIndexRepository extends JpaRepository<PostWithoutIndex, Long> {

	@Query("SELECT p FROM PostWithoutIndex p WHERE p.title = :title")
	List<PostWithoutIndex> findByTitle(@Param("title") String title);
}