package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Articles;

public interface ArticlesService {
	List<Articles> findAll();
	Articles findExistngArticle(String t, String d);
	List<Articles> findArticlesByTitleAndDescription(String k);
	Articles save(Articles a);
	Articles saveAndFlush(Articles a);
	void deleteAll();
	Articles findbytitle(String title);
}
