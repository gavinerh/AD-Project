package com.example.demo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.example.demo.model.dto.CategoryDto;

@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	
//	@ManyToMany(mappedBy="cats", fetch = FetchType.LAZY)
//	private Collection<UserCredential> users;
	
	public Category() {
		super();
		//users = new ArrayList<>();
	}

	public Category(String name) {
		super();
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public Collection<UserCredential> getUsers() {
//		return users;
//	}
//
//	public void setUsers(Collection<UserCredential> users) {
//		this.users = users;
//	}
	public CategoryDto toDto() {
		return new CategoryDto(this.id, this.name, false);
	}
	
	public static Category fromDto(CategoryDto dto) {
		return new Category(dto.getName());
	}
	
	
}
