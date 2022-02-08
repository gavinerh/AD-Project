package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Search {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	private String keyword;
}
