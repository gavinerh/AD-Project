package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.CategoryRepo;
import com.example.demo.model.Category;
import com.example.demo.model.dto.CategoryDto;

@RestController
@CrossOrigin()
@RequestMapping("/category")
public class CategoryController {

	private final CategoryRepo categoryRepo;

	@Autowired
	public CategoryController(CategoryRepo categoryRepo) {
		this.categoryRepo = categoryRepo;
	}

	@GetMapping
	public List<CategoryDto> getCategories() {
		return categoryRepo.findAll().stream().map(Category::toDto).collect(Collectors.toList());
	}

	@PostMapping
	public Long saveCategory(@RequestBody CategoryDto category) {
		return categoryRepo.save(Category.fromDto(category)).getId();
	}

	@DeleteMapping("/{id}")
	public void deleteCategory(@PathVariable("id") Long id) {
		categoryRepo.deleteById(id);
	}
	//hiii
}