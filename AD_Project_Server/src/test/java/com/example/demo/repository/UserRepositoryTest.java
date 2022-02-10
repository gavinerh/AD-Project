package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.AdProjectServerApplication;
import com.example.demo.Repository.ArticlesRepo;
import com.example.demo.Repository.CategoryRepo;
import com.example.demo.Repository.LikedArticleRepository;
import com.example.demo.Repository.SourceRepo;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.Articles;
import com.example.demo.model.Category;
import com.example.demo.model.UserCredential;
import com.example.demo.model.LikedArticle;
import com.example.demo.model.JsonModel.MLJson;
import com.example.demo.service.NewsService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import Enumerates.category;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes=AdProjectServerApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
	
	@Autowired
	UserRepository urepo;
	@Autowired
	CategoryRepo crepo;
	@Autowired
	ArticlesRepo arepo;
	@Autowired
	SourceRepo srepo;
	@Autowired
	LikedArticleRepository larepo;
	
	@Test
	@Order(1)
	public void saveUserTest() {
//		User u1 = new User("gavin", "99999", "gavinerh@gmail.com", "password", "normal");
//		uService.save(u1);
//		List<User> users = uService.findAll();
//		assertEquals(1, users.size());
		
//		category[] cats = category.values();
//		List<Category> Cats = crepo.findAll();
//	    //crepo.saveAllAndFlush(Cats);
//		System.out.println(Cats.get(0).getName());
//		List<Category> pref = new ArrayList<>();
//		Category pref1 = new Category(category.business.name());
//		Category pref2 = new Category(category.sports.name());
//		pref.add(Cats.get(4));
//		pref.add(Cats.get(5));
//		UserCredential user = urepo.findById((long) 59).orElse(null);
//		user.addCat(Cats.get(0));
//		System.out.println(user.getName());
//		urepo.save(user);
//		category[] cats = category.values();
//		List<Category> Cats = crepo.findAll();
//	    //crepo.saveAllAndFlush(Cats);
//		System.out.println(Cats.get(0).getName());
//		List<Category> pref = new ArrayList<>();
//		Category pref1 = new Category(category.business.name());
//		Category pref2 = new Category(category.sports.name());
//		pref.add(Cats.get(4));
//		pref.add(Cats.get(5));
//		User user = urepo.findById((long) 59).orElse(null);
//		user.addCat(Cats.get(0));
//		System.out.println(user.getName());
//		urepo.save(user);
		List<Articles> arlist = arepo.findAll();
//		List<LikedArticle> la = new ArrayList<>();
//		for(int i=0; i<10; i++) {
//			la.add(new LikedArticle(arlist.get(i).getTitle()));
//		}
//		larepo.saveAllAndFlush(la);
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
          List<LikedArticle> llist = larepo.findAll();
          arlist.stream()
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
          for(int i:result.getResult()) {
        	  System.out.println(arlist.get(i).getTitle());
          }
          //disconnect from url connection
          conn.disconnect();

      } catch (MalformedURLException e) {
          e.printStackTrace();
      }catch (IOException e){
e.printStackTrace();
      }
	}
	
//	@Test
//	@Order(2)
//	public void deleteUserTest() {
//		User u = uService.findUserByEmail("gavinerh@gmail.com");
//		int userCountBefore = uService.findAll().size();
//		uService.deleteUser(u);
//		assertEquals(0, userCountBefore - 1);
//	}
	
//	@Test
//	@Order(3)
//	public void testUserNotFound() {
//		User u = uService.findUserByEmail("@gmail.com");
//		if(u != null) {
//			assert(false);
//		}
//		assert(true);
//	}
}
