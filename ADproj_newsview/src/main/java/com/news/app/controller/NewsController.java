package com.news.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news.app.model.Articles;
import com.news.app.model.NewsSet;
import com.news.app.model.Source;
import com.news.app.repo.ArticlesRepo;
import com.news.app.repo.SourceRepo;
import com.news.app.service.NewsService;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/newsapi")
public class NewsController {
	
	@Autowired
	ArticlesRepo arepo;
	@Autowired
	SourceRepo srepo;
	
//	@RequestMapping(value="/")
//	public NewsSet HomePage() {
//		return NewsService.getNewsHome("tech", null, null);
//	}
	
	@RequestMapping(value="/")
	public List<Articles> HomePage() {
		//created results ns1
		NewsSet ns1 = NewsService.getNewsHome("technology", null, null);
		
		//just testing db
		List<Articles> alist = ns1.getArticles();
		for(Articles art:alist) {
			//--> to extract sources if need be
			srepo.save(art.getSource());
			//--> to save bookmarked articles
			arepo.save(art);
		}
		return alist;
	}
	
	@GetMapping("/{country}/{category}")
	public List<Articles> requestByCountryAndCategory(@PathVariable
			String country, @PathVariable String category) {
			NewsSet nsCC = NewsService.getNewsByCountryCategory(country, category, null);
			return nsCC.getArticles();
	}
	
	 
//	@GetMapping(value="/{country}/{category}")
//	public NewsSet requestByCountryAndCategory(@PathVariable String country,
//			@PathVariable String category) throws ParseException, IOException {
//		return NewsService.getNewsByCountryCategory(country, category, null);
//	}
	
	@GetMapping(value="/kw/{keyword}")
	public NewsSet requestByKeywords(@PathVariable String keyword) {
		return NewsService.getNewsByKeyword(keyword, null);
	}
	
//	@GetMapping(value="/src/{source1}")
//	public NewsSet requestBySource(@PathVariable String source1) {
//		return NewsService.getNewsBySource(source1, null);
//	}
		
}
