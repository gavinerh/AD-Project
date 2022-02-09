package com.example.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.ArticlesRepo;
import com.example.demo.Repository.CategoryRepo;
import com.example.demo.Repository.LikedArticleRepository;
import com.example.demo.Repository.SourceRepo;
import com.example.demo.model.Articles;
import com.example.demo.model.Category;
import com.example.demo.model.LikedArticle;
import com.example.demo.model.NewsSet;
import com.example.demo.model.Search;
import com.example.demo.model.UserCredential;
import com.example.demo.model.JsonModel.MLJson;
import com.example.demo.service.ArticlesService;
import com.example.demo.service.NewsService;
import com.example.demo.service.SearchService;
import com.fasterxml.jackson.databind.ObjectMapper;

import Enumerates.category;


@RestController
@CrossOrigin()
@RequestMapping(path="/newsapi")
public class NewsController {
	
	@Autowired
	ArticlesService aService;
	@Autowired
	SearchService srhService;
	@Autowired
	SourceRepo srepo;
	@Autowired
	CategoryRepo crepo;
	@Autowired
	LikedArticleRepository larepo;
	
	private List<Articles> alist = new ArrayList<>();
	
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
	
	//search using USING NEWSAPI, save keywords no duplicates, no saving articles
	@GetMapping(value= {"/kw/updateKeyword"})
	public List<Articles> displayPage(@RequestParam(required=false) 
			String keyword) {
		System.out.println(keyword);

		NewsSet ns = new NewsSet();
		if(keyword!=null) {	
			Search s = new Search(); 
			if(srhService.findWord(keyword)==null) { //check for existing word
				s.setKeyword(keyword);
				srhService.save(s);
			}
			ns = NewsService.getNewsByKeyword(keyword, null);
		} 
		else if(keyword==null) {
			ns = NewsService.getNewsHome("technology", null, null);
		}
		
		List<Articles> alist = ns.getArticles();	
//			for(Articles art:ns.getArticles()) {
//				if(!art.getUrl().contains("blogger.googleusercontent")) {
//					alist.add(art);
//				}
//			}
		return alist;
	}
	
	//For testing
	@RequestMapping(value= {"/kw/{keyword}"})
	public List<Articles> testDisplayPage(@PathVariable(required=false) 
			String keyword) {
		NewsSet ns = new NewsSet();
		if(keyword!=null) {
			ns = NewsService.getNewsByKeyword(keyword, null);
		} else {
			ns = NewsService.getNewsHome("technology", null, null);
		}
		List<Articles> alist = ns.getArticles();	
		return alist;
	}
	
	//For ANDROID TEMPORARY
	@GetMapping("/news")
	public ResponseEntity<List<Articles>> newsPage() {
////////////////////////////////////////////////////////////////////
		//Fetch News from NEWSAPI
		
		//alist = fetchNewsAPI();
////////////////////////////////////////////////////////////////////		
		//Fetch News from database

		alist = aService.findAll();
/////////////////////////////////////////////////////////////////////			
		System.out.println("Fetched Articles size: "+alist.size());
		System.out.println("Fetched Articles size: "+alist.get(1).getPublishedAt());
		List<Articles> android = new ArrayList<>();
		List<LikedArticle> likes = larepo.findAll();
		if(likes.size()>0) {android = mlfunction(likes);}
		
		else {
		for(int i = 0; i<80; i++) {
			android.add(alist.get(i));}
		}
		Collections.shuffle(android,new Random(5));
		System.out.println("Articles for Android size: "+android.size());
		return new ResponseEntity<List<Articles>>(android, HttpStatus.OK);
	}
	
	@PostMapping(path="/like")
	public ResponseEntity<Void> likeNews(@RequestBody Articles article){
		LikedArticle like = larepo.findByTitle(article.getTitle());
		if(like ==null) {
		larepo.saveAndFlush(new LikedArticle(article.getTitle()));}
		else {
			larepo.delete(like);
		}
		System.out.println(article);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@PostMapping(path="/dislike")
	public ResponseEntity<Void> dislikeNews(@RequestBody Articles article) {
		System.out.println(article);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
/////////////////////////////Private Methods///////////////////////////////////////
	private List<Articles> fetchNewsAPI() {
		List<Articles> nlist = new ArrayList<>();
		List<Category> cats = crepo.findAll();
		for(Category s:cats) {
			nlist.addAll(NewsService.getNewsByCountryCategory(s.getName(),null));
		}
		aService.deleteAll();
		srepo.deleteAll();
		for(Articles art:nlist) {
			srepo.save(art.getSource()); //save sources to DB
			aService.save(art); //save articles to DB
		}
		return nlist;
	}

	private List<Articles> mlfunction(List<LikedArticle> llist) {
		List<Articles> mlList = new ArrayList<>();
		try {
	          URL url = new URL("http://127.0.0.1:5000/like");
	          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	          conn.setRequestMethod("POST");
	          conn.setRequestProperty("Content-Type", "application/json; utf-8");
	          conn.setRequestProperty("Accept","application/json");
	          conn.setDoOutput(true);
	          
	          ObjectMapper mapper = new ObjectMapper();      
	          MLJson ldlike = new MLJson();
	          
	          List<String> titles = new ArrayList<>();
	          List<String> likes = new ArrayList<>();
	          alist.stream()
	          		.forEach(x-> titles.add(x.getTitle()));
	         
	          llist.stream()
	          		.forEach(x-> likes.add(x.getTitle()));
	          
	          ldlike.setTitles(titles);
	          ldlike.setLikedNews(likes);
	          System.out.println(ldlike.getLikedNews().size()+"\n"+ldlike.getTitles().size());
	          OutputStream os = conn.getOutputStream();
	          byte[] input = mapper.writeValueAsBytes(ldlike);
	          os.write(input,0,input.length);
	     
	          if (conn.getResponseCode() != 200) {
	              throw new RuntimeException("Failed : HTTP error code : "
	                      + conn.getResponseCode());
	          }

	          InputStream is = conn.getInputStream();
	          MLJson result = mapper.readValue(is, MLJson.class);
	         
	          System.out.println(result.getResult());
	          System.out.println(result.getResult().size());
	          //disconnect from url connection
	          conn.disconnect();
	          for(int i:result.getResult()) {
	        	  mlList.add(alist.get(i));
	          }

	      } catch (MalformedURLException e) {
	          e.printStackTrace();
	      }catch (IOException e){
	e.printStackTrace();
	      }
		return mlList;
	}
}		



