package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.BookmarkedArticles;
import com.example.demo.model.UserCredential;

public interface BookmarkedArticlesRepository extends JpaRepository<BookmarkedArticles, String> {
	public BookmarkedArticles findByTitle(String title);
	
	@Query("SELECT b FROM BookmarkedArticles b, User u WHERE u=:user"
			+ "AND b.title like :t")
	public BookmarkedArticles findByUserAndTitle(@Param("user") UserCredential user, 
			@Param("t") String title);
	
	@Query("SELECT l FROM BookmarkedArticles l, User u WHERE u.id =:id")
	public List<BookmarkedArticles> findByUser(@Param("id") UserCredential userid);
	
}