package com.gd0t.gd0t.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gd0t.gd0t.model.Post;
import com.gd0t.gd0t.repository.PostRepository;

@Service
public class PostService {
	
	private final PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	// get all posts for the home page of the blog
	public List<Post> getAllPosts() {
		return postRepository.findAllByOrderByIdDesc(); // using the proxy class at runtime that is created
	}
	
	// get a single post for reading
	public Optional<Post> getPostById(Long id) {
		return postRepository.findById(id);
	}
	
	// saving a post or updating existing one
	public Post savePost(Post post) {
		// TODO: business logic
		
		return postRepository.save(post);
	}
	
	// deleting a post
	public void deletePost(Long id) {
		postRepository.deleteById(id);
	}
}
