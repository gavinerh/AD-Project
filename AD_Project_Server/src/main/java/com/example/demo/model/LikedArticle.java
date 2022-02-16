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
	@ManyToOne
	private UserCredential user;

	public LikedArticle(String title, String urlimg, String url,String desc,UserCredential user) {
		super();
		this.title = title;
		this.url = url;
		this.urlToImage=urlimg;
		this.user = user;
		description = desc;
	}


}
