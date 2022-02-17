package com.example.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.example.demo.Repository.BookmarkedArticlesRepository;
import com.example.demo.Repository.CategoryRepo;
import com.example.demo.Repository.DislikedArticleRepository;
import com.example.demo.Repository.LikedArticleRepository;
import com.example.demo.Repository.SourceRepo;
import com.example.demo.model.Articles;
import com.example.demo.model.BookmarkedArticles;
import com.example.demo.model.DislikedArticle;
import com.example.demo.model.LikedArticle;
import com.example.demo.model.NewsSet;
import com.example.demo.model.UserCredential;
import com.example.demo.model.JsonModel.CategoryJson;
import com.example.demo.model.JsonModel.MLJson;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.ArticlesService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.NewsService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
//<<<<<<< Updated upstream
//=======
//import Enumerates.category;
//>>>>>>> Stashed changes


@RestController
@CrossOrigin()
@RequestMapping(path="/newsapi")
public class NewsController {
	@Autowired
	UserService uService;
	@Autowired
	BookmarkedArticlesRepository bmrepo;
	@Autowired
	ArticlesService aService;
	@Autowired
	SourceRepo srepo;
	@Autowired
	CategoryRepo crepo;
	@Autowired
	LikedArticleRepository larepo;
	@Autowired
	DislikedArticleRepository darepo;
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	CategoryService cService;
	
	private List<Articles> alist = new ArrayList<>();
	
	//For WEB TEMPORARY
	@RequestMapping(value="/")
	public List<Articles> HomePage() {
		NewsSet ns = NewsService.getNewsHome("technology", null, null);
		List<Articles> alist = aService.findAll();//ns.getArticles();
//		for(Articles art:alist) {
//			//check if articles exist in  DB
//			if(aService.findExistngArticle(art.getTitle(), art.getDescription())==null) {
//				srepo.save(art.getSource()); //save sources to DB
//				aService.save(art); //save articles to DB
//			}
//		}
		System.out.println("News Articles "+alist.size());
		return alist;
	}
	
	//search using NEWSAPI
	@GetMapping(value= {"/kw/updateKeyword"})
	public List<Articles> displayPage(@RequestParam Map<String,String> requestParams) {
		String keyword = requestParams.get("keyword");
		String sorting = requestParams.get("sortBy");
		System.out.println(keyword + sorting);

		NewsSet ns = new NewsSet();
		if(keyword!=null) {	
			ns = NewsService.getNewsByKeyword(keyword, sorting, null, null); //no date, no key
		} 
		else if(keyword==null) {
			System.out.println("keyword is null"); 
			ns = NewsService.getNewsHome("technology", null, null);
		}
		
		List<Articles> alist = ns.getArticles();	
		return alist;
	}
	
	//search from database
//	@GetMapping(value= {"/kw/updateKeyword"})
//	public List<Articles> searchFromDB(@RequestParam Map<String,String> requestParams) {
//		String keyword = requestParams.get("keyword");
//		String sorting = requestParams.get("sortBy");
//		System.out.println(keyword + sorting);
//		
//		NewsSet ns = new NewsSet();
//		List<Articles> searchList = new ArrayList<>();
//		if(keyword!=null) {	
//			searchList = aService.findArticlesByTitleAndDescription(keyword);
//		} else if(keyword==null) {
//			System.out.println("keyword is null"); 
//			ns = NewsService.getNewsHome("technology", null, null);
//			searchList = ns.getArticles();
//		}
//		return searchList;
//	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	//For ANDROID TEMPORARY
	@GetMapping("/news")
	public ResponseEntity<?> newsPage(HttpServletRequest request) {
		UserCredential user = finduser(request);
///////////////////////////////////////////////////////////////////////	
		//Fetch News from database

		alist = aService.findAll();
///////////////////////////////////////////////////////////////////////

		System.out.println("Fetched Articles size: "+alist.size());
		List<Articles> android = new ArrayList<>();
		List<LikedArticle> likes = larepo.findByUser(user);
		List<DislikedArticle> dislikes = darepo.findByUser(user);
		List<BookmarkedArticles> bms = bmrepo.findByUser(user);
		
		if(likes.size()>1 || dislikes.size()>0) {android = mlfunction(likes,dislikes);}
		
		else {
			if(alist.size()>50) {
		for(int i = 0; i<100; i++) {
			android.add(alist.get(i));}}
			else {android = alist;}
		}
		List<String> like = new ArrayList<>();
		List<String> dislike = new ArrayList<>();
		List<String> bm = new ArrayList<>();
		likes.stream().forEach(x-> like.add(x.getTitle()));
		dislikes.stream().forEach(x-> dislike.add(x.getTitle()));
		bms.stream().forEach(x-> bm.add(x.getTitle()));
		
		
		Map<String,List<?>> aj = new HashMap<String,List<?>>();
		aj.put("news", android);
		aj.put("likes", like);
		aj.put("dislikes", dislike);
		aj.put("bookmarks", bm);
		
		System.out.println("Articles for Android size: "+android.size());
		return new ResponseEntity<Map<String,List<?>>>(aj, HttpStatus.OK);
	}
	//create method to retrieve bookmarked articles
		@GetMapping(path="/bmpreference")
		public ResponseEntity<?> getbmpref(HttpServletRequest request) {
			UserCredential user = finduser(request);
			List<BookmarkedArticles> bookmarks = bmrepo.findByUser(user);

			Map<String,List<?>> bmpref = new HashMap<String,List<?>>();
			bmpref.put("bookmarks", bookmarks);	
			return new ResponseEntity<Map<String, List<?>>>(bmpref, HttpStatus.OK);
		}	
	@GetMapping(path="/preference")
	public ResponseEntity<?> getpref(HttpServletRequest request){
		UserCredential user = finduser(request);
		
		List<LikedArticle> likes = larepo.findByUser(user);
		List<DislikedArticle> dislikes = darepo.findByUser(user);
		
		Map<String,List<?>> pref = new HashMap<String,List<?>>();
		pref.put("likes", likes);
		pref.put("dislikes", dislikes);
		
		return new ResponseEntity<Map<String,List<?>>>(pref,HttpStatus.OK);
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@PostMapping(path="/bookmark/{email}")
	public ResponseEntity<Void> bookmarkNews(@RequestBody Articles article, 
			@PathVariable("email") String email){

		UserCredential user = uService.findUserByEmail(email);
		BookmarkedArticles bookmarked = bmrepo.findByUserAndTitle(user,article.getTitle());
		
		if(bookmarked==null) {
			bmrepo.saveAndFlush(new BookmarkedArticles(article.getTitle(), 
					article.getUrl(),article.getUrlToImage(), user));}
		else {
			bmrepo.delete(bookmarked);
		}
		System.out.println(article);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping(path="/bookmark")
	public ResponseEntity<?> getbookmarks(HttpServletRequest request){
		UserCredential user = finduser(request);
		
		List<BookmarkedArticles> bms = bmrepo.findByUser(user);
		
		return new ResponseEntity<List<BookmarkedArticles>>(bms,HttpStatus.OK);
	}
	
	@PostMapping(path="/like")
	public ResponseEntity<Void> likeNews(HttpServletRequest request,@RequestBody Articles article){
		UserCredential user = finduser(request);
		
		LikedArticle like = larepo.findByUserAndTitle(user,article.getTitle());
		if(like ==null) {
		larepo.saveAndFlush(new LikedArticle(article.getTitle(),article.getUrlToImage(),
				article.getUrl(),article.getDescription(),user));}
		else {
			larepo.delete(like);
		}
		System.out.println(article);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PostMapping(path="/dislike")
	public ResponseEntity<Void> dislikeNews(HttpServletRequest request,@RequestBody Articles article) {
		UserCredential user = finduser(request);
		DislikedArticle dislike = darepo.findByUserAndTitle(user,article.getTitle());
		if(dislike ==null) {
		darepo.saveAndFlush(new DislikedArticle(article.getTitle(),article.getUrlToImage(),
				article.getUrl(),article.getDescription(),user));}
		else {
			darepo.delete(dislike);
		}
		System.out.println(article);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(path="/category")
	public List<CategoryJson> getCategories(HttpServletRequest request){
		String email = finduser(request).getEmail();
		HashMap<String, Boolean> map = cService.getAllCategoriesByUser(email);
		List<CategoryJson> list = new ArrayList<>();
		for(String s : map.keySet()) {
			CategoryJson cj = new CategoryJson(s, map.get(s));
			list.add(cj);
		}
		return list;
	}
	
	@PostMapping(path="/category")
	public ResponseEntity<Void> postCategories(HttpServletRequest request, @RequestBody List<CategoryJson> res){
		System.out.println(res);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
/////////////////////////////Private Methods///////////////////////////////////////

	private List<Articles> mlfunction(List<LikedArticle> llist, List<DislikedArticle> dlist) {
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
	          List<String> dislikes = new ArrayList<>();
	          alist.stream()
	          		.forEach(x-> {
	          			titles.add(x.getTitle()+" "+x.getDescription()==null? "":x.getDescription());
	  	          			});
	         if(llist!=null) {
	          llist.stream()
	          		.forEach(x-> likes.add(x.getTitle()+" "+x.getDescription()==null? "":x.getDescription()));}
	         if(dlist!=null) {
	      	   dlist.stream().forEach(x-> dislikes.add(x.getTitle()+" "+x.getDescription()==null? "":x.getDescription()));
	         }
	          
	          ldlike.setTitles(titles);
	          ldlike.setLikedNews(likes);
	          ldlike.setDislikedNews(dislikes);
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
	
	/////Find User/////
	private UserCredential finduser(HttpServletRequest request) {
		String authenticationHeader = request.getHeader("Authorization");
		String email = null;
		String jwt = null;
		if(authenticationHeader != null && authenticationHeader.startsWith("Bearer")) {
			jwt = authenticationHeader.substring(7);
			email = jwtUtil.extractUsername(jwt);
		}
		UserCredential user = uService.findUserByEmail(email);
		System.out.println(user.getEmail());
		return user;
	}
}		



