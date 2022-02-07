package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Comment;


public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
