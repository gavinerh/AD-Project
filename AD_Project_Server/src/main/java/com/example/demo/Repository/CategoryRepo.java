package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
	@Query("SELECT c FROM Category c join c.users u WHERE u.email = :email")
	List<Category> getAllCategoriesByUser(@Param("email") String email);
}
