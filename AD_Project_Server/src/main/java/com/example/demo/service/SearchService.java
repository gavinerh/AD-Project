package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Search;

public interface SearchService {
	List<Search> findAll();
	Search findWord(String keyword);
	Search save(Search s);
	void deleteAll();
}
