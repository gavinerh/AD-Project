
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
	@Column(length=512)
	private String title;
	@Column(length=512)
	private String description;
	@Column(length=512)
	private String url;
	@ManyToOne
	private UserCredential user;
	@Column(length=1000)
	private String UrlToImage;


	public DislikedArticle(String title, String description, String url, UserCredential user, String UrlToImage) {

		super();
		this.title = title;
		this.url = url;
		this.user = user;
		this.UrlToImage = UrlToImage;
		this.description=description;
	}
	//hiii
}
