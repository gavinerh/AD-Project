
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
	private int id;
	private String title;
	@Column(length=512)
	private String description;
	private String url;
	@ManyToOne
	private UserCredential user;
	private String UrlToImage;

	public DislikedArticle(String title, String description, String url, UserCredential user, String UrlToImage) {
		super();
		this.title = title;
		this.description = description;
		this.url = url;
		this.user = user;
		this.UrlToImage = UrlToImage;
	}
	public DislikedArticle(String title, String url,UserCredential user) {
		this(title,null,url,user);
	}

}
