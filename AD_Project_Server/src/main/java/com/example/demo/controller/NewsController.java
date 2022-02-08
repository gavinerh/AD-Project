package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.ArticlesRepo;
import com.example.demo.Repository.CategoryRepo;
import com.example.demo.Repository.SourceRepo;
import com.example.demo.model.Articles;
import com.example.demo.model.Category;
import com.example.demo.model.NewsSet;
import com.example.demo.model.User;
import com.example.demo.service.ArticlesService;
import com.example.demo.service.NewsService;
import Enumerates.category;


@RestController
@CrossOrigin()
@RequestMapping(path="/newsapi")
public class NewsController {
	
	@Autowired
	ArticlesService aService;
	@Autowired
	SourceRepo srepo;
	@Autowired
	CategoryRepo crepo;
	
	//For WEB TEMPORARY
	@RequestMapping(value="/")
	public List<Articles> HomePage() {
		NewsSet ns = NewsService.getNewsHome("technology", null, null);
		List<Articles> alist = ns.getArticles();
		for(Articles art:alist) {
			//check if articles exist in  DB
			if(aService.findExistngArticle(art.getTitle(), art.getDescription())==null) {
				srepo.save(art.getSource()); //save sources to DB
				aService.save(art); //save articles to DB
			}
		}
		return alist;
	}
	
	//For ANDROID TEMPORARY
	@GetMapping("/news")
	public ResponseEntity<List<Articles>> newsPage() {
		
		List<Articles> alist = new ArrayList<>();
////////////////////////////////////////////////////////////////////		
		//Fetch News from NEWSAPI
		//alist = fetchNewsAPI();
/////////////////////////////////////////////////////////////////////
		
		//Fetch News from database
		alist = aService.findAll();
		
		System.out.println("Fetched Articles size: "+alist.size());
		List<Articles> android = new ArrayList<>();
	
		for(int i = 0; i<80; i++) {
			android.add(alist.get(i));
		}
		Collections.shuffle(android);
		System.out.println("Articles for Android size: "+android.size());
		return new ResponseEntity<List<Articles>>(android, HttpStatus.OK);
	}
	
	private List<Articles> fetchNewsAPI() {
		List<Articles> nlist = new ArrayList<>();
		List<Category> cats = crepo.findAll();
		for(Category s:cats) {
			nlist.addAll(NewsService.getNewsByCountryCategory(s.getName(),null));
		}
		aService.deleteAll();
		srepo.deleteAll();
		for(Articles ar:nlist) {
			srepo.saveAndFlush(ar.getSource());
			aService.saveAndFlush(ar);
		}
		return nlist;
	}
}		
	


