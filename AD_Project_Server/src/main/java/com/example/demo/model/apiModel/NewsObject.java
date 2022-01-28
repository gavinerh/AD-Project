package com.example.demo.model.apiModel;

import java.util.ArrayList;
import java.util.Arrays;

public class NewsObject {
	private String status;
	private int totalResults;
	private ArrayList<NewContent> articles;
	public void setArticles(ArrayList<NewContent> articles) {
		this.articles = articles;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

	@Override
	public String toString() {
		return "NewsObject [status=" + status + ", totalResults=" + totalResults + ", articles=" + articles + "]";
	}
	public ArrayList<NewContent> getArticles() {
		return articles;
	}
	
	
}
