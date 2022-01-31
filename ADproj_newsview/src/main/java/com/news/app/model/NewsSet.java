package com.news.app.model;

import java.util.ArrayList;

import javax.persistence.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewsSet {
	private String status;
	private Integer totalResults;
	private ArrayList<Articles> articles;

}
