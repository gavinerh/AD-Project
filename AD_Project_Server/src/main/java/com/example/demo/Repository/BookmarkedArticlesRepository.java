package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.BookmarkedArticles;

public interface BookmarkedArticlesRepository extends JpaRepository<BookmarkedArticles, String> {
	public BookmarkedArticles findByTitle(String title);
}