package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Articles;

public interface ArticlesRepo extends JpaRepository<Articles, Integer>{
	
	public List<Articles> findByTitleContains(String keyword);

	@Query("SELECT a FROM Articles a WHERE a.title like :t" 
			+" AND a.description like :d")
	public Articles findExistingArticlesByWord(@Param("t") String title, @Param("d") String desc);
	
	@Query("SELECT a FROM Articles a WHERE a.title like %:keyword%" 
			+" OR a.description like %:keyword%")
	List<Articles> findByTitleDescription(@Param("keyword") String k);
	
	@Query("SELECT a FROM Articles a WHERE a.title like :t" )
	public Articles findArticlesBytitle(@Param("t")String title);
	
	@Query("SELECT a FROM Articles a WHERE a.category in (:list)")
	public List<Articles> findByCategoryIn(@Param("list") List<Long> categories);
}

