package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Articles {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	@ManyToOne(fetch=FetchType.EAGER)
	private Source source;	
	private String author;
	private String title;
	@Column(length=512)
	private String description;
	private String url;
	@Column(length=512)
	private String urlToImage;
	private String publishedAt;
	private String content;

}

