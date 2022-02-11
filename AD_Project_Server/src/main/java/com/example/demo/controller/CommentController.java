package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.CategoryRepo;
import com.example.demo.Repository.CommentRepo;
import com.example.demo.Repository.LikedArticleRepository;
import com.example.demo.Repository.SourceRepo;
import com.example.demo.model.Articles;
import com.example.demo.model.Comment;
import com.example.demo.model.NewsSet;
import com.example.demo.model.UserCredential;
import com.example.demo.service.ArticlesService;
import com.example.demo.service.CommentService;
import com.example.demo.service.NewsService;


@CrossOrigin()
@RestController
public class CommentController {

	@Autowired
	ArticlesService aService;
	

	@Autowired
	CommentService cService;



	@Autowired
	CommentRepo commentRepo; 
	
	
	
	
	
	
	@PostMapping(path="/comment")
	public void comment(@RequestBody Comment comment){
		System.out.println(comment);
	    String symbol = comment.getUsername();
	    int pos = symbol.indexOf("@");
	    System.out.println(symbol.substring(0, pos));
		
		comment.setUsername(symbol.substring(0, pos));
		
		
		commentRepo.save(comment);
	    
	    Articles a = aService.findbytitle(comment.getTitle());
	    System.out.println(a.getDescription());
	
	
	}
	
	
	
	
	
	@PostMapping(path ="/getComment")
	public List<Comment> getComments(@RequestBody Comment comment) {
	    
		
	//	System.out.println(comment.getTitle());
	//	System.out.println(cService.findcommtbytitle(comment.getTitle()));
		
		return	cService.findcommtbytitle(comment.getTitle());
		
		
	}
	
	//@GetMapping(value="/getComment")
	//public List<Comment> getComments(@RequestParam String title) {
	//    
		
	//	return	cService.findcommtbytitle(title);
		
		
	//}
	
	
	
	
}
