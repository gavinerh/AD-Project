package com.example.demo.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class BookmarkedArticles {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String title;
	private String url;
	private String description;
	
	@ManyToOne
//	@JoinColumns({
//		@JoinColumn(name="user_email", referencedColumnName="EMAIL")	
//	})
	private UserCredential user;
//	
//	private String userEmail;
//	public BookmarkedArticles(String title, String url, String userEmail) {
//		this.title=title;
//		this.url=url;
//		this.userEmail=userEmail;
//	}
	public BookmarkedArticles(String title, String url, UserCredential user) {
		this.title=title;
		this.url=url;
		this.user=user;
		
	}
}
