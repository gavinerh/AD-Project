package com.example.demo.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserCredential;
import com.example.demo.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	UserService uService;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserCredential user = uService.findUserByEmail(username);
//		UserCredential user = new UserCredential();
		return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
	}
	

}
