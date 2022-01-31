package com.news.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Source {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int sourceid;
	private String id;
	private String name;

}
