package com.gd0t.gd0t.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.gd0t.gd0t.model.Post;
import com.gd0t.gd0t.service.MarkdownService;
import com.gd0t.gd0t.service.PostService;

@Controller
public class BlogController {
	
	@Autowired
	private MarkdownService markdownService;
	
	@Autowired
	private final PostService postService;
	
	// Dependency Injection -> Spring gives us the Service
	public BlogController(PostService postService) {
		this.postService = postService;
	}
	
	@GetMapping("/")
	public String homePage(Model model) {
		
		// 1. Fetch all posts from the database using the post service
		List<Post> posts = postService.getAllPosts();
		
		model.addAttribute("posts", posts);
		
		// Returning the name of the HTML file I want to render (without the .html extension)
		return "index";
	}
	
	@GetMapping("/new")
	public String showCreateForm(Model model) {
		// I pass a completely empty, new Post object to the HTML form, so I can render the form
		model.addAttribute("post", new Post());
		return "create-post";
	}
	
	@PostMapping("/new")
	public String createPost(@ModelAttribute("post") Post post) {
		postService.savePost(post);
		
		// tells the browser to navigate back to the home page
		return "redirect:/";
	}
	
	@GetMapping("/post/{id}")
	public String viewSinglePost(@PathVariable Long id, Model model) {
		Post post = postService.getPostById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid post Id:" + id));
		
		String formattedHtml = markdownService.renderToHtml(post.getContent());
		
		model.addAttribute("post", post);
		model.addAttribute("formattedContent", formattedHtml);
		return "post";
	}
	
	@GetMapping("/post/{id}/edit")
	public String showEditForm(@PathVariable Long id, Model model) {
		Post post = postService.getPostById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid post Id:" + id));
		
		model.addAttribute("post", post);
		return "edit-post";
	}
	
	@PostMapping("/post/{id}/edit")
	public String updatePost(@PathVariable Long id, @ModelAttribute("post") Post updatedPost) {
		Post postToEdit = postService.getPostById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid post Id:" + id));
		
		postToEdit.setTitle(updatedPost.getTitle());
		postToEdit.setContent(updatedPost.getContent());
		
		postService.savePost(postToEdit);
		
		return "redirect:/post/" + id;
	}
	
	@PostMapping("/post/{id}/delete")
	public String deletePost(@PathVariable Long id) {
		postService.deletePost(id);
		
		return "redirect:/";
	}
}
