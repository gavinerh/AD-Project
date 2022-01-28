package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path="/account")
public class AccountController {
	@Autowired
	UserService uService;
	
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User user){
		User storedUser = uService.findUserByEmail(user.getEmail());
		if(storedUser == null) return new ResponseEntity<User>(user, HttpStatus.UNAUTHORIZED);
		if(storedUser.getEmail().equals(user.getEmail()) && storedUser.getPassword().equals(user.getPassword())) {
			return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<User>(user, HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user){
		// validation of user
		if(uService.findUserByEmail(user.getEmail()) == null) {
			uService.save(user);
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		}
		return new ResponseEntity<User>(user, HttpStatus.CONFLICT);
	}
	
	@GetMapping("/{email}")
	public ResponseEntity<User> getUser(@PathVariable String email){
		User u = uService.findUserByEmail(email);
		if(u == null) return new ResponseEntity<User>(u, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<User> update(@RequestBody User user){
		System.out.println(user);
		User u = uService.findUserByEmail(user.getEmail());
		u.setName(user.getName());
		u.setPassword(user.getPassword());
		u.setPhone(user.getPhone());
		uService.save(u);
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}
}
