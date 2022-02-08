package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.SearchRepository;
import com.example.demo.model.Search;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	SearchRepository sRepo;
	
	@Override
	public List<Search> findAll() {
		return sRepo.findAll();
	}

	@Override
	public Search save(Search s) {
		return sRepo.save(s);
	}
	
	@Override
	public Search findWord(String keyword) {
		return sRepo.findSearchWord(keyword);
	}

	@Override
	public void deleteAll() {
		sRepo.deleteAll();
	}
}
