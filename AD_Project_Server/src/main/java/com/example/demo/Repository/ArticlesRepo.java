package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Articles;

public interface ArticlesRepo extends JpaRepository<Articles, Integer>{
	public List<Articles> findByTitleContains(String keyword);
}

