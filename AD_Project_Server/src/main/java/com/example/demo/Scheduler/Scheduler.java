
package com.example.demo.Scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.demo.Repository.CategoryRepo;
import com.example.demo.Repository.SourceRepo;
import com.example.demo.model.Articles;
import com.example.demo.model.Category;
import com.example.demo.model.NewsSet;
import com.example.demo.service.ArticlesService;
import com.example.demo.service.NewsService;

import Enumerates.category;

@Configuration
@EnableScheduling
public class Scheduler {
	@Autowired
	CategoryRepo crepo;
	@Autowired
	ArticlesService aService;
	@Autowired
	SourceRepo srepo;
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("h:mm:ss a");

	//// RETRIEVE ARTICLES FROM API/////
	@Scheduled(cron = "50 31 01 * * ?")

	public void scheduleTaskUsingCronExpression() {
		
	    System.out.println(
	      "Starting scheduled tasks using cron jobs (Fetch news from API) - " + dateTimeFormatter.format(LocalDateTime.now()));
	    
	    List<Articles> nlist = new ArrayList<>();
	    List<Articles> aList = new ArrayList<>();
 	    List<category> cats = Arrays.asList(category.values());
		for(category s:cats) {
			//NewsSet ns = NewsService.getNewsHome(s.name(), null, null);
			
			aList = NewsService.getNewsByCountryCategory(s.name(), null);
			/*
			 * for (Articles a:aList) { a.setCategoty(s); }
			 */
			nlist.addAll(aList);
		}
		System.out.println("Fetched Articles (size): "+nlist.size());

		List<Articles> clean = checkDupes(nlist);
		System.out.println("Cleaned articles : "+clean.size());
		aService.saveall(clean);
		List<Articles> db = aService.findAll();
		if(db.size()>0) {
			System.out.println("Articles from Database (size): "+db.size());
		}
		else {
			System.out.println("No Articles from Database");
		}
		System.out.println(
			      "Scheduled tasks Done - " + dateTimeFormatter.format(LocalDateTime.now()));
	}
	
	/////DELETE OLD ARTICLES////
	
	@Scheduled(cron = "00 31 01 * * ?")
	public void scheduleTaskToDeleteOldArticles() {
		
	    System.out.println(
	      "Starting scheduled tasks using cron jobs_2 - " + dateTimeFormatter.format(LocalDateTime.now()));
	    List<Articles> db = aService.findAll();
	    List<Articles> toDelete = new ArrayList<>();
	    System.out.println("Fetched Articles from database size: "+db.size());
	    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
	    LocalDate now = LocalDate.now();
	    db.stream().forEach(x-> {
	    	LocalDate date = LocalDate.parse(x.getPublishedAt(), df);
	    	if(date.plusDays(3).isEqual(now)) {
	    		toDelete.add(x);
	    	}
	    });
	    System.out.println("Articles to Delete Size: "+toDelete.size());
	    aService.deleteAll(toDelete);
	    System.out.println("Articles from database after Delete: "+aService.findAll().size());
	    
	    
	    
//	    List<Articles> nlist = new ArrayList<>();
//	    List<Category> cats = crepo.findAll();
//		for(Category s:cats) {
//			nlist.addAll(NewsService.getNewsByCountryCategory(s.getName(),null));
//		}
//		System.out.println("Fetched Articles (size): "+nlist.size());
//		aService.deleteAll();
//		srepo.deleteAll();
//		for(Articles art:nlist) {
//			srepo.save(art.getSource()); //save sources to DB
//			aService.save(art); //save articles to DB
//		}
//		List<Articles> db = aService.findAll();
//		if(db.size()>0) {
//			System.out.println("Articles from Database (size): "+db.size());
//		}
//		else {
//			System.out.println("No Articles from Database");
//		}
		System.out.println(
			      "Scheduled tasks Done - " + dateTimeFormatter.format(LocalDateTime.now()));
	}
	private List<Articles> checkDupes(List<Articles> artlist){
		List<Articles> alist = new ArrayList<>();
		List<Articles> dblist = aService.findAll();
		List<Integer> index = new ArrayList<>();
		List<String> st = new ArrayList<>();
		for(Articles art:artlist) {
			String[] t = art.getTitle().split("-");
			String u = "";
			if(t.length>1) {
			t = Arrays.copyOf(t,t.length-1);
			u = String.join("", t);
			}
			else {u=t[0];}
			if(!st.contains(u)) {
				st.add(u);
				index.add(artlist.indexOf(art));
			}
		}
		for(int i:index) {
			alist.add(artlist.get(i));
		}
		if (dblist==null) {return alist;}
		else {
		List<Articles> clean = alist.stream()
								.filter(x-> !dblist.contains(x))
								.collect(Collectors.toList());
		return clean;
		}
	}

}
