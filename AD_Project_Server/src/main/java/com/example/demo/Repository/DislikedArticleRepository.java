package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.DislikedArticle;
import com.example.demo.model.UserCredential;

public interface DislikedArticleRepository extends JpaRepository<DislikedArticle, Integer> {
	public DislikedArticle findByTitle(String title);
	public List<DislikedArticle> findByUser(UserCredential user);
	public DislikedArticle findByUserAndTitle(UserCredential user, String title);

}
