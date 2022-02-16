package com.example.demo.model;

import java.util.Collection;

import javax.persistence.Column;
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
	@Column(length=512)
	private String title;
	@Column(length=1000)
	private String urlToImage;
	@Column(length=512)
	private String url;
	
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
	public BookmarkedArticles(String title, String url,String urlimg, UserCredential user) {
		this.title=title;
		this.url=url;
		this.user=user;
		this.urlToImage=urlimg;
	}
}
