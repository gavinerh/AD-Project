package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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
		

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
		String zt = ZonedDateTime.now().format(dateTimeFormatter);
		
//		List<Articles> artlist = arepo.findAll();
//		List<Articles> alist = new ArrayList<>();
//		List<Integer> index = new ArrayList<>();
//		List<String> st = new ArrayList<>();
//		System.out.println(artlist.get(1).getTitle());
//
//		for(Articles art:artlist) {
//			String[] t = art.getTitle().split("-");
//			String u = "";
//			if(t.length>1) {
//			t = Arrays.copyOf(t,t.length-1);
//			u = String.join("", t);
//			}
//			else {u=t[0];}
//			if(!st.contains(u)) {
//				st.add(u);
//			}
//			else if(st.contains(u)) {index.add(artlist.indexOf(art));}
//		}
//		for(int i:index) {
//			alist.add(artlist.get(i));
//		}
		category[] cat = category.values();
		List<Category> cats = new ArrayList<>();
		for(category c:cat) {
			cats.add(new Category(c.name()));
		}
		crepo.saveAll(cats);
		
//		System.out.println(artlist.get(1).getTitle()+"\n"+alist.get(1).getTitle());
//		System.out.println(alist.size()+"\t\t"+index.size());
//		System.out.println(zt);
		
//		System.out.println(arlist.size()+"\n"+alist.size()+"\n"+st.size()+"\n"+index.size());
//		arlist.stream().forEach(x->{
//			System.out.println(x.getTitle());		
//			});
//		List<LikedArticle> la = new ArrayList<>();
//		for(int i=0; i<10; i++) {
//			la.add(new LikedArticle(arlist.get(i).getTitle()));
//		}
//		larepo.saveAllAndFlush(la);
//		try {
//          URL url = new URL("http://127.0.0.1:5000/like");
//          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//          conn.setRequestMethod("POST");
//          conn.setRequestProperty("Content-Type", "application/json; utf-8");
//          conn.setRequestProperty("Accept","application/json");
//          conn.setDoOutput(true);
//          
//          ObjectMapper mapper = new ObjectMapper();      
//          MLJson ldlike = new MLJson();
//          
//          List<String> titles = new ArrayList<>();
//          List<String> likes = new ArrayList<>();
//          List<LikedArticle> llist = larepo.findAll();
//          arlist.stream()
//          		.forEach(x-> titles.add(x.getTitle()));
//         
//          llist.stream()
//          		.forEach(x-> likes.add(x.getTitle()));
//          
//          ldlike.setTitles(titles);
//          ldlike.setLikedNews(likes);
//          System.out.println(ldlike.getLikedNews().size()+"\n"+ldlike.getTitles().size());
//          OutputStream os = conn.getOutputStream();
//          byte[] input = mapper.writeValueAsBytes(ldlike);
//          os.write(input,0,input.length);
//     
//          if (conn.getResponseCode() != 200) {
//              throw new RuntimeException("Failed : HTTP error code : "
//                      + conn.getResponseCode());
//          }
//
//          InputStream is = conn.getInputStream();
//          MLJson result = mapper.readValue(is, MLJson.class);
//         
//          System.out.println(result.getResult());
//          System.out.println(result.getResult().size());
//          for(int i:result.getResult()) {
//        	  System.out.println(arlist.get(i).getTitle());
//          }
//          //disconnect from url connection
//          conn.disconnect();
//
//      } catch (MalformedURLException e) {
//          e.printStackTrace();
//      }catch (IOException e){
//e.printStackTrace();
//      }
//	}
	
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
}}
