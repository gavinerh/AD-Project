package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Comment;


public interface CommentRepo extends JpaRepository<Comment, Integer>{
      
	@Query("SELECT c FROM Comment c WHERE c.title like :t")
	List<Comment> findCommentsbytitle(@Param("t")String title);
}
