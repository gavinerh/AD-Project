package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class LikedArticle {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(length=512)
	private String title;
	@Column(length=512)
	private String description;
	@Column(length=1000)
	private String urlToImage;
	@Column(length=512)
	private String url;
	private String UrlToImage;
	@ManyToOne
	private UserCredential user;

<<<<<<< HEAD
	public LikedArticle() {
		super();
	}
	public LikedArticle(String title,String url,UserCredential user) {
		this(title,null,url,user);
	}

	public LikedArticle(String title, String description, String url,UserCredential user, String UrlToImage) {
=======
	public LikedArticle(String title, String urlimg, String url,String desc,UserCredential user) {
>>>>>>> 3f0a9427a4eaa1bfe6b46f9adca87975e62956e9
		super();
		this.title = title;
		this.url = url;
		this.urlToImage=urlimg;
		this.user = user;
<<<<<<< HEAD
		this.UrlToImage = UrlToImage;
=======
		description = desc;
>>>>>>> 3f0a9427a4eaa1bfe6b46f9adca87975e62956e9
	}


}
