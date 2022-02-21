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
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.BookmarkedArticlesRepository;
import com.example.demo.Repository.DislikedArticleRepository;
import com.example.demo.Repository.LikedArticleRepository;
import com.example.demo.Repository.SourceRepo;
import com.example.demo.Scheduler.Scheduler;
import com.example.demo.model.Articles;
import com.example.demo.model.BookmarkedArticles;
import com.example.demo.model.Category;
import com.example.demo.model.DislikedArticle;
import com.example.demo.model.LikedArticle;
import com.example.demo.model.NewsSet;
import com.example.demo.model.UserCredential;
import com.example.demo.model.JsonModel.CategoryJson;
import com.example.demo.model.JsonModel.MLJson;
import com.example.demo.model.JsonModel.ReactJson;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.ArticlesService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.FetchingService;
import com.example.demo.service.NewsService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
@CrossOrigin()
@RequestMapping(path="/newsapi")
public class NewsController {
	@Autowired
	UserService uService;
	@Autowired
	CategoryService cService;
	@Autowired
	BookmarkedArticlesRepository bmrepo;
	@Autowired
	ArticlesService aService;
	@Autowired
	SourceRepo srepo;
	@Autowired
	LikedArticleRepository larepo;
	@Autowired
	DislikedArticleRepository darepo;
	@Autowired
	JwtUtil jwtUtil;
	List<Articles> alist = new ArrayList<Articles>();
	@Autowired
	Scheduler sched;
	@Autowired
	FetchingService fService;
	
	//testing
	
	
	
	//For WEB TEMPORARY
	/*
	 * @RequestMapping(value="/") public List<Articles> HomePage() { List<Articles>
	 * alist = NewsService.getNewsByCountryCategory("Technology", null);
	 * for(Articles art:alist) { //check if articles exist in DB
	 * if(aService.findExistngArticle(art.getTitle(), art.getDescription())==null) {
	 * srepo.save(art.getSource()); //save sources to DB aService.save(art); //save
	 * articles to DB } } System.out.println("News Articles "+alist.size()); return
	 * alist; }
	 */

//	@RequestMapping(value="/")
//	public List<Articles> HomePage() {
//		NewsSet ns = NewsService.getNewsHome("technology", null, null);
//		List<Articles> alist = aService.findAll();//ns.getArticles();
////		for(Articles art:alist) {
////			//check if articles exist in  DB
////			if(aService.findExistngArticle(art.getTitle(), art.getDescription())==null) {
////				srepo.save(art.getSource()); //save sources to DB
////				aService.save(art); //save articles to DB
////			}
////		}
//		System.out.println("News Articles "+alist.size());
//		return alist;
//	}
//	

	
	 @RequestMapping(value = "/") 
	  public List<ReactJson> HomePage(HttpServletRequest request) {
		 // NewsSet ns =NewsService.getNewsHome("technology", null, null);
		 if(aService.findAll().isEmpty()) {
			 fService.populateArticles();
		 }
		  UserCredential user = finduser(request);
		  List<Articles> alist=aService
				  .findAll()
				  .stream()
				  .filter(article -> user.getCats().contains(article.getCategory()))
				  .collect(Collectors.toList()); 
//		  for (Articles art : alist) { 
//			  if( user.getCats().contains(art.getCategory())) { 
//				  // check if articles exist in DB 
//				  if (aService.findExistngArticle(
//						  art.getTitle(), art.getDescription()) == null) {
//					  srepo.save(art.getSource());
//					  aService.save(art);
//					  // save sources
//				  }
//			  }
//			  System.out.println("News Articles " + alist.size());
//	  
//		  } 
		  List<ReactJson> newslist = new ArrayList<ReactJson>();
		  for (Articles a: alist)
		  {
			  ReactJson temp = new ReactJson(a.getId(), a.getSource(), a.getAuthor(), a.getTitle(), a.getDescription(), a.getUrl(),
						a.getUrlToImage(), a.getPublishedAt(), a.getPrettytime(), a.getContent(),a.getComments(),
					false, a.getCategory(), false, false);
		
			  LikedArticle like = larepo.findByUserAndTitle(user,temp.getTitle());
			  DislikedArticle dislike = darepo.findByUserAndTitle(user,temp.getTitle());
			  BookmarkedArticles bookmarked = bmrepo.findByUserAndTitle(user,temp.getTitle());
				
			  if(like != null)
			  {
				  temp.setIsliked(true);
			  }
			  if(dislike != null)
			  {
				  temp.setIsdisliked(true);
			  }
			  if(bookmarked != null)
			  {
				  temp.setIsbookmarked(true);
			  }
			  
			  System.out.println(temp);
			  newslist.add(temp);
		  }
		 
		  return newslist; 
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

		List<Articles> alist = aService
				  .findAll()
				  .stream()
				  .filter(article -> user.getCats().contains(article.getCategory()))
				  .collect(Collectors.toList()); 
       
		System.out.println("Fetched Articles size: "+alist.size());
		List<Articles> android = new ArrayList<>();
		List<LikedArticle> likes = larepo.findByUser(user);
		List<DislikedArticle> dislikes = darepo.findByUser(user);
		List<BookmarkedArticles> bms = bmrepo.findByUser(user);
		
		if(likes.size()>10 || dislikes.size()>10) {android = mlfunction(likes,dislikes);}
		
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
	@PostMapping(path="/bookmark")
	public ResponseEntity<Void> bookmarkNews(HttpServletRequest request,@RequestBody Articles article){

		UserCredential user = finduser(request);
		BookmarkedArticles bookmarked = bmrepo.findByUserAndTitle(user,article.getTitle());
		
		if(bookmarked==null) {

			bmrepo.saveAndFlush(new BookmarkedArticles(article.getTitle(),article.getUrl(),article.getDescription(), article.getUrlToImage(),
					 user, article.getPublishedAt()));}

		else {
			bmrepo.delete(bookmarked);
		}
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping(path="/bookmarked")
	public ResponseEntity<?> getbookmarks(HttpServletRequest request){
		UserCredential user = finduser(request);
		
		List<BookmarkedArticles> bms = bmrepo.findByUser(user);
		System.out.println("bookmarked "+bms.size());
		
		return new ResponseEntity<List<BookmarkedArticles>>(bms,HttpStatus.OK);
	}
		
	@GetMapping(path="/liked")
	public ResponseEntity<?> getliked(HttpServletRequest request){
		UserCredential user = finduser(request);
		
		List<LikedArticle> bms = larepo.findByUser(user);
		
		System.out.println("liked "+bms.size());
		
		
		return new ResponseEntity<List<LikedArticle>>(bms,HttpStatus.OK);
	}
	
	@GetMapping(path="/disliked")
	public ResponseEntity<?> getdisliked(HttpServletRequest request){
		UserCredential user = finduser(request);
		
		List<DislikedArticle> bms = darepo.findByUser(user);
		
		return new ResponseEntity<List<DislikedArticle>>(bms,HttpStatus.OK);
	}
	
	@PostMapping(path="/like")
	public ResponseEntity<Void> likeNews(HttpServletRequest request,@RequestBody Articles article){
		UserCredential user = finduser(request);
		DislikedArticle dislike = darepo.findByUserAndTitle(user, article.getTitle());
		LikedArticle like = larepo.findByUserAndTitle(user,article.getTitle());
		if (dislike != null) 
		{
			darepo.delete(dislike);
			
		} 
		if(like ==null) {

		larepo.saveAndFlush(new LikedArticle(article.getTitle(),article.getDescription(),article.getUrl(),user,article.getUrlToImage()));}

		
		else {
			larepo.delete(like);
		}
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PostMapping(path="/checkLike")
	public ResponseEntity<Boolean> IsArticleLiked(HttpServletRequest request,@RequestBody Articles article){
		UserCredential user = finduser(request);
		Boolean checking = false;
		LikedArticle like = larepo.findByUserAndTitle(user,article.getTitle());
		if(like ==null) {
		checking=false;}
		else {
			checking=true;
		}
		System.out.println(checking);
		
		return new ResponseEntity<Boolean>(checking,HttpStatus.OK);
	}
	
	@PostMapping(path="/dislike")
	public ResponseEntity<Void> dislikeNews(HttpServletRequest request,@RequestBody Articles article) {
		UserCredential user = finduser(request);
		DislikedArticle dislike = darepo.findByUserAndTitle(user,article.getTitle());
		LikedArticle like = larepo.findByUserAndTitle(user,article.getTitle());
		if(like!=null) {
			larepo.delete(like);
		}
		if(dislike ==null) {

		darepo.saveAndFlush(new DislikedArticle(article.getTitle(),article.getDescription(),article.getUrl(),user,article.getUrlToImage()));}

	

		else {
			darepo.delete(dislike);
		}	
		
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
	
	@GetMapping(path="/cats")
	public List<String> getcats(){
		List<Category> category = cService.getAllCategories();
		List<String> cats = new ArrayList<>();
		category.stream().forEach(x-> cats.add(x.getName()));
		return cats;
	}
	
//	@PostMapping(path="/category")
//	public ResponseEntity<Void> postCategories(HttpServletRequest request, @RequestBody List<CategoryJson> res){
//		System.out.println(res);
//		return new ResponseEntity<Void>(HttpStatus.OK);
//	}
	
	@PostMapping(path = "/category")
	public ResponseEntity<Void> postCategories(HttpServletRequest request, @RequestBody List<CategoryJson> res) {
		System.out.println(res);
		UserCredential user = finduser(request);
		if(res==null) {
			List<Category> categorys=cService.getAllCategories();
			for (Category a: categorys) {
				user.getCats().add(a);
			}
		}
		else {
		for (CategoryJson a : res) {
			Category category = cService.finCategoryByName(a.getName());
			if (a.isSelect() == true && !user.getCats().contains(category)) {

				user.getCats().add(category);
			} else if (a.isSelect() == false && user.getCats().contains(category)) {
				user.getCats().remove(category);
			}
		}
		}
		
		uService.save(user);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping(path = "/admin/category")
	public List<Category> getAdmincategory() {
		return cService.getAllCategories();
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



