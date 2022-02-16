package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.DislikedArticle;
import com.example.demo.model.UserCredential;

public interface DislikedArticleRepository extends JpaRepository<DislikedArticle, Integer> {
	public DislikedArticle findByTitle(String title);
	
	@Query("SELECT l FROM DislikedArticle l, User u WHERE u=:user"
			+"AND l.title like :t")
	public DislikedArticle findByUserAndTitle(@Param("user") UserCredential user, 
			@Param("t") String title);
	
	@Query("SELECT l FROM DislikedArticle l, User u WHERE u.id =:id")
	public List<DislikedArticle> findByUser(@Param("id") UserCredential userid);
}
