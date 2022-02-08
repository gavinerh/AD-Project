package com.example.demo.service;

import java.util.List;

import com.example.demo.model.UserCredential;

public interface UserService {
	List<UserCredential> findAll();
	
	UserCredential findById(Long id);
	
	UserCredential findUserByEmail(String email);
	
	UserCredential save(UserCredential user);
	
	void deleteUser(UserCredential user);
}
