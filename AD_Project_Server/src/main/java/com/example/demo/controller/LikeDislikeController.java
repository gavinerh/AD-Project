package com.example.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Articles;
@CrossOrigin()
@RestController
@RequestMapping("/practice")
public class LikeDislikeController {
	@PostMapping(path="/like")
	public void likeNews(@RequestBody Articles article){
		// register the like news from client side
		
	}
	
	@PostMapping(path="/dislike")
	public void dislikeNews(@RequestBody Articles article) {
		// register the disliked news from client side
	}
	
	@GetMapping(path="/search")
	public void search(@RequestParam String keyword) {
		System.out.println(keyword);
	}
}
