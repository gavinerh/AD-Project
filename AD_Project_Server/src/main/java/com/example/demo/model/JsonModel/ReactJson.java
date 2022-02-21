package com.example.demo.model.JsonModel;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.example.demo.model.Articles;
import com.example.demo.model.Category;
import com.example.demo.model.Comment;
import com.example.demo.model.Source;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
// json for communication with react and if possible for android
public class ReactJson extends Articles {
	
	private boolean isliked;
	private boolean isbookmarked;
	private boolean isdisliked;
	private Source source;	
	private String author;
	private String title;
	private String description;
	private String url;
	private String urlToImage;
	private String publishedAt;
	private String prettytime;
	private String content;
	private List<Comment> comments;	
	private Category category;
	private int id;
	
	
	
	@Override
	public String toString() {
		return "ReactJson [imageUrl=" + urlToImage + ", webpageUrl=" + url + ", title=" + title + ", description="
				+ description + "]";
	}

	public ReactJson(int id, Source source, String author, String title, String description, String url,
			String urlToImage, String publishedAt, String prettytime, String content, List<Comment> comments,
			boolean isliked, Category category, boolean isdisliked, boolean isbookmarked) {
		super();
		this.id = id;
		this.source = source;
		this.author = author;
		this.title = title;
		this.description = description;
		this.url = url;
		this.urlToImage = urlToImage;
		this.publishedAt = publishedAt;
		this.prettytime = prettytime;
		this.content = content;
		this.comments = comments;
		this.isliked = isliked;
		this.category = category;
		this.isdisliked= isdisliked;
		this.isbookmarked = isbookmarked;
	}
	
	
}
