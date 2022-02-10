package com.example.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Articles;
import com.example.demo.model.Comment;
import com.example.demo.model.JsonArticle;
import com.example.demo.model.UserCredential;
@CrossOrigin()
@RestController
public class LikeDislikeController {
	@PostMapping(path="/like")
	public void likeNews(@RequestBody JsonArticle article){
		System.out.println(article);
		// register the like news from client side
		
	}
	
	@PostMapping(path="/dislike")
	public void dislikeNews(@RequestBody JsonArticle article) {
		System.out.println(article);
		// register the disliked news from client side
	}
	
//	@PostMapping(path="/like")
//	public void likeNews1(@RequestBody Message message){
//		System.out.println("Liked this article");
//		// register the like news from client side
//		
//	}
//	
//	@PostMapping(path="/dislike")
//	public void dislikeNews1(@RequestBody Message message) {
//		System.out.println("disliked this article");
//		// register the disliked news from client side
//	}
	
	@GetMapping(path="/search")
	public void search(@RequestParam String keyword) {
		System.out.println(keyword);
	}
	
	
	
}
