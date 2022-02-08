package com.example.demo.model;

import java.time.Instant;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.ocpsoft.prettytime.PrettyTime;

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
	@Column(length=512)
	private String url;
	@Column(length=1000)
	private String urlToImage;
	private String publishedAt;
	private String content;
	@OneToMany(fetch=FetchType.EAGER)
	private List<Comment> comments;	
	
	
	public String getPublishedAt() {
		Instant dateTime = Instant.parse(publishedAt);
		PrettyTime p = new PrettyTime();
		return p.format(dateTime);
	}

}

