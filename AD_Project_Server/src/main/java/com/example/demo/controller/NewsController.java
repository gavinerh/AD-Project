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
import com.example.demo.model.UserCredential;
import com.example.demo.service.NewsService;
import Enumerates.category;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path="/newsapi")
public class NewsController {
	
	@Autowired
	ArticlesRepo arepo;
	@Autowired
	SourceRepo srepo;
	@Autowired
	CategoryRepo crepo;
	
//	@RequestMapping(value="/")
//	public NewsSet HomePage() {
//		return NewsService.getNewsHome("tech", null, null);
//	}
	
	@GetMapping(value="/")
	public ResponseEntity<List<Articles>> HomePage() {
		//created results ns1
		NewsSet ns1 = NewsService.getNewsHome("technology", null, null);
		
		//just testing db
		List<Articles> alist = ns1.getArticles();
		System.out.println(alist.get(1).getTitle());
//		for(Articles art:alist) {
//			//--> to extract sources if need be
//			srepo.save(art.getSource());
//			//--> to save bookmarked articles
//			arepo.save(art);
//		}
		return new ResponseEntity<List<Articles>>(alist, HttpStatus.OK);
	}
	@GetMapping("/news")
	public ResponseEntity<List<Articles>> newsPage() {
		
		List<Articles> alist = new ArrayList<>();
////////////////////////////////////////////////////////////////////		
		//Fetch News from NEWSAPI
		//alist = fetchNewsAPI();
/////////////////////////////////////////////////////////////////////
		
		//Fetch News from database
		alist = arepo.findAll();
		
		System.out.println("Fetched Articles size: "+alist.size());
		List<Articles> android = new ArrayList<>();
	
		for(int i = 0; i<80; i++) {
			android.add(alist.get(i));
		}
		Collections.shuffle(android);
		System.out.println("Articles for Android size: "+android.size());
		return new ResponseEntity<List<Articles>>(android, HttpStatus.OK);
	}
	
//	@GetMapping("/{country}/{category}")
//	public List<Articles> requestByCountryAndCategory(@PathVariable
//			String country, @PathVariable String category) {
//			NewsSet nsCC = NewsService.getNewsByCountryCategory(country, category);
//			return nsCC.getArticles();
//	}
	
	 
//	@GetMapping(value="/{country}/{category}")
//	public NewsSet requestByCountryAndCategory(@PathVariable String country,
//			@PathVariable String category) throws ParseException, IOException {
//		return NewsService.getNewsByCountryCategory(country, category, null);
//	}
	
//	@GetMapping(value="/kw/{keyword}")
//	public NewsSet requestByKeywords(@PathVariable String keyword) {
//		return NewsService.getNewsByKeyword(keyword, null);
//	}
//	
//	@GetMapping(value="/src/{source1}")
//	public NewsSet requestBySource(@PathVariable String source1) {
//		return NewsService.getNewsBySource(source1, null);
//	}
	private List<Articles> fetchNewsAPI() {
		List<Articles> nlist = new ArrayList<>();
		List<Category> cats = crepo.findAll();
		for(Category s:cats) {
			nlist.addAll(NewsService.getNewsByCountryCategory(s.getName(),null));
		}
		arepo.deleteAll();
		srepo.deleteAll();
		for(Articles ar:nlist) {
			srepo.saveAndFlush(ar.getSource());
			arepo.saveAndFlush(ar);
		}
		return nlist;
	}
}		
	


