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
	private Integer id;
	@Column(length=512)
	private String title;
	@Column(length=512)
	private String description;
	@Column(length=1000)
	private String urlToImage;
	@Column(length=512)
	private String url;
	@ManyToOne
	private UserCredential user;

	public DislikedArticle(String title, String urlimg, String url,String desc, UserCredential user) {
		super();
		this.title = title;
		this.urlToImage = urlimg;
		this.url = url;
		this.user = user;
		this.description = desc;
	}

}
