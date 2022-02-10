package com.example.demo.Scheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.demo.Repository.CategoryRepo;
import com.example.demo.Repository.SourceRepo;
import com.example.demo.model.Articles;
import com.example.demo.model.Category;
import com.example.demo.service.ArticlesService;
import com.example.demo.service.NewsService;

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
	@Scheduled(cron = "0 01 12 * * ?")
	public void scheduleTaskUsingCronExpression() {
		
	    System.out.println(
	      "Starting scheduled tasks using cron jobs_1 - " + dateTimeFormatter.format(LocalDateTime.now()));
	    
	    List<Articles> nlist = new ArrayList<>();
	    List<Category> cats = crepo.findAll();
		for(Category s:cats) {
			nlist.addAll(NewsService.getNewsByCountryCategory(s.getName(),null));
		}
		System.out.println("Fetched Articles (size): "+nlist.size());
		aService.deleteAll();
		srepo.deleteAll();
		for(Articles art:nlist) {
			srepo.save(art.getSource()); //save sources to DB
			aService.save(art); //save articles to DB
		}
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
	@Scheduled(cron = "10 51 22 * * ?")
	public void scheduleTaskUsingCronExpression_2() {
		
	    System.out.println(
	      "Starting scheduled tasks using cron jobs_2 - " + dateTimeFormatter.format(LocalDateTime.now()));
	    
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

}
