package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.BookmarkedArticles;
import com.example.demo.model.UserCredential;

public interface BookmarkedArticlesRepository extends JpaRepository<BookmarkedArticles, String> {
	public BookmarkedArticles findByTitle(String title);
	public List<BookmarkedArticles> findByUser(UserCredential user);
	public BookmarkedArticles findByUserAndTitle(UserCredential user, String title);
	
}