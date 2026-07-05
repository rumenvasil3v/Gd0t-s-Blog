package com.gd0t.gd0t.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gd0t.gd0t.model.Post;

// interface that acts as a gateway to the database post table and save posts to the post table, find posts, etc.

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
	List<Post> findAllByOrderByIdDesc();
}
