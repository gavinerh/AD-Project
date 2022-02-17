package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.AdProjectServerApplication;
import com.example.demo.service.CategoryService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes=AdProjectServerApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)

public class CategoryRepositoryTest {
	@Autowired
	CategoryService cService;

//	@Test
//	@Order(1)
//	public void saveCategoryTest() {
//		Category c = new Category();
//		c.setName("technology");
//		cService.save(c);
//		Category c2 = new Category();
//		c2.setName("business");
//		cService.save(c2);
//		Category c3 = new Category(); c3.setName("entertainment");
//		cService.save(c3);
//		Category c4 = new Category(); c4.setName("general");
//		cService.save(c4);
//		Category c5 = new Category(); c5.setName("science");
//		cService.save(c5);
//		Category c6 = new Category(); c6.setName("health");
//		cService.save(c6);
//		Category c7 = new Category(); c7.setName("sports");
//		cService.save(c7);
//		List<Category> list = cService.getAllCategories();
//		assertEquals(7, list.size());
//	}
	
	@Test
	public void getCategoriesByUserTest() {
		HashMap<String, Boolean> map = cService.getAllCategoriesByUser("test@gmail.com");
		System.out.println(map);
	}
}
