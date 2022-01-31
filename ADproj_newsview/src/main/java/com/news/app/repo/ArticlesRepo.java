package com.news.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.news.app.model.Articles;

public interface ArticlesRepo extends JpaRepository<Articles, Integer>{
	public List<Articles> findByTitleContains(String keyword);
}
