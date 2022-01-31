package com.news.app.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.news.app.model.Source;
//Jackson library is for processing JSON for data binding, converting JSON string to obj
//can parse JSON from string, stream or file
//create JAVA obj representing parsed JSON

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
	private String description;
	private String url;
	private String urlToImage;
	private String publishedAt;
	private String content;

}
