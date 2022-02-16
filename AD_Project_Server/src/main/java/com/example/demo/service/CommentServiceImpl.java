package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.CommentRepo;
import com.example.demo.model.Comment;


@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	CommentRepo cRepo;
	
	@Override
	public List<Comment>findcommtbytitle(String title){
		return cRepo.findCommentsbytitle(title);
		
	};
	
	
	@Override
	public void deleteComment(String title,String username,String content,String commenttime) {
		cRepo.delete(cRepo.deleteComment(title,username,content,commenttime));
		
		
		
	}

}
