
package com.example.demo.model;

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
	private String title;
	private String description;
	private String url;
	@ManyToOne
	private UserCredential user;

	public DislikedArticle(String title, String description, String url, UserCredential user) {
		super();
		this.title = title;
		this.description = description;
		this.url = url;
		this.user = user;
	}
	public DislikedArticle(String title, String url,UserCredential user) {
		this(title,null,url,user);
	}

}
