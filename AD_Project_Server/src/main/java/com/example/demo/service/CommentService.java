package com.example.demo.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.example.demo.model.Comment;

public interface CommentService {
	List<Comment> findcommtbytitle(String title);
	void deleteComment(String title,String username,String content,String commenttime);

}
