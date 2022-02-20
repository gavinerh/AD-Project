package com.example.demo.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@Column(length=512)
	private String url;
	@Column(length=512)
	private String description;	
	@Column(length=1000)
	private String UrlToImage;
	private String prettytime;
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE}, fetch=FetchType.EAGER)
	private Source source;	
	private String publishedAt;
	@ManyToOne
	private UserCredential user;


	public BookmarkedArticles(String title, String url, String description, String UrlToImage, UserCredential user, String PublishAt ) 
	{
		this.title=title;
		this.url=url;
		this.user=user;
		this.UrlToImage = UrlToImage;
		this.description =description;
	

	}
}
