package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Comment;


public interface CommentRepo extends JpaRepository<Comment, Integer>{
      
	@Query("SELECT c FROM Comment c WHERE c.title like :t")
	List<Comment> findCommentsbytitle(@Param("t")String title);
	
	@Query("SELECT c FROM Comment c WHERE  c.title like :t"+" AND c.commentcontent like :co"+" AND c.username like :name"+" AND c.commenttime like :time")
	Comment deleteComment(@Param("t")String title,@Param("name")String username,@Param("co")String content,@Param("time")String commenttime);
	
}
