package com.example.demo.service;

import java.util.HashMap;
import java.util.List;

import com.example.demo.model.Category;

public interface CategoryService {
	void save(Category c);
	HashMap<String, Boolean> getAllCategoriesByUser(String email);
	List<Category> getAllCategories();
	Category finCategoryByName(String name);
}
