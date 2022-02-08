package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Search;

public interface SearchRepository extends CrudRepository<Search, String> {
	List<Search> findAll();

	@Query("SELECT s FROM Search s WHERE s.keyword =:keyword")
	Search findSearchWord(@Param("keyword") String keyword);
}
