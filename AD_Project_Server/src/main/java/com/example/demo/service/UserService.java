package com.example.demo.service;

import java.util.List;

import com.example.demo.model.User;

public interface UserService {
	List<User> findAll();
	
	User findById(Long id);
	
	User findUserByEmail(String email);
	
	User save(User user);
	
	void deleteUser(User user);
}
