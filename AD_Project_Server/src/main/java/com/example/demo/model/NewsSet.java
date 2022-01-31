package com.example.demo.model;

import java.util.ArrayList;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewsSet {
	private String status;
	private Integer totalResults;
	private ArrayList<Articles> articles;

}

