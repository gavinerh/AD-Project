package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Comment;

public interface CommentService {
	List<Comment> findcommtbytitle(String title);

}
