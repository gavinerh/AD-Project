package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.DislikedArticle;

public interface DislikedArticleRepository extends JpaRepository<DislikedArticle, Integer> {

}
