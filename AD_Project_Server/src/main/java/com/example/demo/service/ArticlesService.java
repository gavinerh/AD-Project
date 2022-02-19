package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Articles;

public interface ArticlesService {
	List<Articles> findAll();
	Articles findExistngArticle(String t, String d);
	List<Articles> findArticlesByTitleAndDescription(String k);
	List<Articles> findArticlesByCategories(List<Long> categories);
	Articles save(Articles a);
	Articles saveAndFlush(Articles a);
	void deleteAll();
	void deleteAll(List<Articles> art);
	Articles findbytitle(String title);
	void delete(Articles art);
	void saveall(List<Articles> alist);
}
