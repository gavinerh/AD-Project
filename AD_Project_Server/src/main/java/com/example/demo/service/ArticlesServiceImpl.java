package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Repository.ArticlesRepo;
import com.example.demo.model.Articles;

@Service
public class ArticlesServiceImpl implements ArticlesService {
	@Autowired
	ArticlesRepo aRepo;
	
	@Override
	public Articles save(Articles a) {
		return aRepo.save(a);
	}
	@Override
	public Articles saveAndFlush(Articles a) {
		return aRepo.saveAndFlush(a);
	}
	@Override
	public List<Articles> findArticlesByTitleAndDescription(String k) {
		return aRepo.findByTitleDescription(k);
	}
	@Override
	public Articles findExistngArticle(String title, String desc) {
		return aRepo.findExistingArticlesByWord(title, desc);
	}
	@Override
	public List<Articles> findAll() {
		return aRepo.findAll();
	}
	@Transactional
	@Override
	public void deleteAll() {
		aRepo.deleteAll();
	}
	@Override
	public Articles findbytitle(String title) {
		
		return aRepo.findArticlesBytitle(title);
	}
	@Transactional
	@Override
	public void delete(Articles art) {
		// TODO Auto-generated method stub
		aRepo.delete(art);
		
	}
	@Transactional
	@Override
	public void deleteAll(List<Articles> art) {
		// TODO Auto-generated method stub
		aRepo.deleteAll(art);
		
	}
	@Override
	public void saveall(List<Articles> alist) {
		// TODO Auto-generated method stub
		aRepo.saveAllAndFlush(alist);
		
	}
	@Override
	public List<Articles> findArticlesByCategories(List<Long> categories) {
		return aRepo.findByCategoryIn(categories);
	}
	
}
