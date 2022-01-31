package com.news.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.news.app.model.Source;

public interface SourceRepo extends JpaRepository<Source, Integer>{

}
