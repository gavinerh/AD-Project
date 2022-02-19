
package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class DislikedArticle {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
<<<<<<< HEAD
	private int id;
=======
	private Integer id;
	@Column(length=512)
>>>>>>> 3f0a9427a4eaa1bfe6b46f9adca87975e62956e9
	private String title;
	@Column(length=512)
	private String description;
	@Column(length=1000)
	private String urlToImage;
	@Column(length=512)
	private String url;
	@ManyToOne
	private UserCredential user;
	private String UrlToImage;

<<<<<<< HEAD
	public DislikedArticle(String title, String description, String url, UserCredential user, String UrlToImage) {
=======
	public DislikedArticle(String title, String urlimg, String url,String desc, UserCredential user) {
>>>>>>> 3f0a9427a4eaa1bfe6b46f9adca87975e62956e9
		super();
		this.title = title;
		this.urlToImage = urlimg;
		this.url = url;
		this.user = user;
<<<<<<< HEAD
		this.UrlToImage = UrlToImage;
	}
	public DislikedArticle(String title, String url,UserCredential user) {
		this(title,null,url,user);
=======
		this.description = desc;
>>>>>>> 3f0a9427a4eaa1bfe6b46f9adca87975e62956e9
	}

}
