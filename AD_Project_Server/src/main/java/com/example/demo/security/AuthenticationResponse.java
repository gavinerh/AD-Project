package com.example.demo.security;

public class AuthenticationResponse {
	private String jwt;
	private String email;

	public AuthenticationResponse(String jwt, String email) {
		super();
		this.jwt = jwt;
		this.email = email;
	}

	public String getJwt() {
		return jwt;
	}

	public String getEmail() {
		return email;
	}

	

	
}
