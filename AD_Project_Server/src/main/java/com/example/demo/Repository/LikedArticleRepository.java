package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.LikedArticle;
import com.example.demo.model.UserCredential;

public interface LikedArticleRepository extends JpaRepository<LikedArticle, Integer> {
	public LikedArticle findByTitle(String title);
	
	@Query("SELECT l FROM LikedArticle l, UserCredential u WHERE u=:user"
			+ " AND l.title like :t")
	public LikedArticle findByUserAndTitle(@Param("user") UserCredential user, 
			@Param("t") String title);

	@Query("SELECT l FROM LikedArticle l, UserCredential u WHERE u.id =:id")
	public List<LikedArticle> findByUser(@Param("id") UserCredential userid);

}
