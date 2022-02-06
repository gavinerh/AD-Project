package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.example.demo.Repository.CategoryRepo;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.Category;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

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
	
	@Test
	@Order(1)
	public void saveUserTest() {
//		User u1 = new User("gavin", "99999", "gavinerh@gmail.com", "password", "normal");
//		uService.save(u1);
//		List<User> users = uService.findAll();
//		assertEquals(1, users.size());
		
		category[] cats = category.values();
		List<Category> Cats = crepo.findAll();
	    //crepo.saveAllAndFlush(Cats);
		System.out.println(Cats.get(0).getName());
		List<Category> pref = new ArrayList<>();
		Category pref1 = new Category(category.business.name());
		Category pref2 = new Category(category.sports.name());
		pref.add(Cats.get(4));
		pref.add(Cats.get(5));
		User user = urepo.findById((long) 59).orElse(null);
		user.addCat(Cats.get(0));
		System.out.println(user.getName());
		urepo.save(user);
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
