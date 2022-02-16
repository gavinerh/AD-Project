package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.LikedArticle;
import com.example.demo.model.UserCredential;

public interface LikedArticleRepository extends JpaRepository<LikedArticle, Integer> {
	public LikedArticle findByTitle(String title);
	public List<LikedArticle> findByUser(UserCredential user);
	public LikedArticle findByUserAndTitle(UserCredential user, String title);
}
