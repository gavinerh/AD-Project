package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserCredential;
import com.example.demo.security.AuthenticationResponse;
import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;

@RestController
@CrossOrigin()
@RequestMapping(path="/account")
public class AccountController {
	@Autowired
	UserService uService;
	
	@Autowired
	private CustomUserDetailsService userDetailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping(value="/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody UserCredential user) throws Exception{
		System.out.println(user);
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		} catch (BadCredentialsException e) {
			return new ResponseEntity<>(new AuthenticationResponse(null, null), HttpStatus.UNAUTHORIZED);
		}
		
		UserDetails userDetails = userDetailService.loadUserByUsername(user.getEmail());
		String jwt = jwtUtil.generateToken(userDetails);
		return new ResponseEntity<>(new AuthenticationResponse(jwt, user.getEmail()), HttpStatus.ACCEPTED);
	}
	
	@PostMapping(value="/register")
	public ResponseEntity<?> register(@RequestBody UserCredential user){
		if(uService.findUserByEmail(user.getEmail()) == null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(user.getPassword()));
			uService.save(user);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else{return new ResponseEntity<>(HttpStatus.CONFLICT);}
	}
	
	@GetMapping("/{email}")
	public ResponseEntity<UserCredential> getUser(@PathVariable String email){
		UserCredential u = uService.findUserByEmail(email);
		if(u == null) return new ResponseEntity<UserCredential>(u, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<UserCredential>(u, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<UserCredential> update(@RequestBody UserCredential user){
		System.out.println(user);
		UserCredential u = uService.findUserByEmail(user.getEmail());
		u.setName(user.getName());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		u.setPassword(encoder.encode(user.getPassword()));
		u.setPhone(user.getPhone());
		uService.save(u);
		return new ResponseEntity<UserCredential>(u, HttpStatus.OK);
	}
	
}
