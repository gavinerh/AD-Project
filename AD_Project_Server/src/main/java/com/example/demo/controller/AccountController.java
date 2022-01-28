package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path="/account")
public class AccountController {
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User user){
		System.out.println(user);
		return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user){
		// validation of user
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
}
