package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Articles;

@RestController
@RequestMapping("/newsapi")
public class LikeDislikeController {
	@PostMapping(path="/like")
	public void likeNews(@RequestBody Articles article){
		// register the like news from client side
		
	}
	
	@PostMapping(path="/dislike")
	public void dislikeNews(@RequestBody Articles article) {
		// register the disliked news from client side
	}
}
