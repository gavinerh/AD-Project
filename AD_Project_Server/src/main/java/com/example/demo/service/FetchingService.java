package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.CategoryRepo;
import com.example.demo.Scheduler.Scheduler;
import com.example.demo.model.Articles;
import com.example.demo.model.Category;
import com.example.demo.model.NewsSet;

@Service
public class FetchingService {
	
	@Autowired
	ArticlesService aService;
	
	@Autowired
	NewsService nService;
	
	@Autowired
	Scheduler sched;
	
	@Autowired
	CategoryRepo crepo;
	
	public void populateArticles(Category category) {
//		List<Articles> nlist = new ArrayList<>();
//	    List<Articles> aList = new ArrayList<>();
// 	    List<Category> cats = crepo.findAll();
//		for(Category s:cats) {
//			//NewsSet ns = NewsService.getNewsHome(s.name(), null, null);
//			
//			aList = NewsService.getNewsByCountryCategory(s.getName(), null);
//			
//			  for (Articles a:aList) 
//			  { 
//				  a.setCategory(s);; 
//			  }
//			 
//			nlist.addAll(aList);
//		}
//		System.out.println("Fetched Articles (size): "+nlist.size());

//		List<Articles> clean = sched.checkDupes(nlist);
//		System.out.println("Cleaned articles : "+clean.size());
//		List<Articles> nlist = new ArrayList<>();
//		NewsSet ns = nService.getNewsHome(category, localDate, null);
		NewsSet ns = nService.getNewsHome(category.getName(), null, null);
		for(Articles a : ns.getArticles()) {
			a.setCategory(category);
		}
		aService.saveall(ns.getArticles());
//		List<Articles> db = aService.findAll();
	}
}
