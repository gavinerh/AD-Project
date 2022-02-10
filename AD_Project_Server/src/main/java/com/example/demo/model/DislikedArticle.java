package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

	public DislikedArticle(String title, String description, String url) {
		super();
		this.title = title;
		this.description = description;
		this.url = url;
	}
	public DislikedArticle(String title, String url) {
		this(title,null,url);
	}

}
