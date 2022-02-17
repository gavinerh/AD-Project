package com.example.demo.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.CategoryRepo;
import com.example.demo.model.Category;
import com.example.demo.security.JwtUtil;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepo cRepo;
	
	@Autowired
	JwtUtil util;
	
	@Override
	public void save(Category c) {
		cRepo.save(c);		
	}

	@Override
	public HashMap<String, Boolean> getAllCategoriesByUser(String email) {
		List<Category> categories = getAllCategories();
		List<Category> userCategories = cRepo.getAllCategoriesByUser(email);
		HashMap<String, Boolean> map = new HashMap<>();
		if(userCategories.size() > 0) {
			for(Category c : userCategories) {
				map.put(c.getName(), true);
			}
			for(Category c : categories) {
				if(!map.containsKey(c.getName())) {
					map.put(c.getName(), false);
				}
			}
		}else {
			for(Category c : categories) {
				map.put(c.getName(), false);
			}
		}
		return map;
	}

	@Override
	public List<Category> getAllCategories() {
		return cRepo.findAll();
	}

}
