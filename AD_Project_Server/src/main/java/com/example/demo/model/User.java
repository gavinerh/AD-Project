package com.example.demo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String phone;
	private String email;
	private String password;
	// admin or normal user
	private String userType;
	@ManyToMany(cascade = {CascadeType.MERGE}, fetch=FetchType.EAGER)
	@JoinTable(name = "usercats", joinColumns = {
			@JoinColumn(name = "userid", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "categoryid", referencedColumnName = "id") })
	private Collection<Category> cats;
	
	public User(String name, String phone, String email, String password, String userType) {
		super();
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.userType = userType;
	}

	public User() {
		super();
		cats = new ArrayList<>();
	}


	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public Collection<Category> getCats() {
		return cats;
	}

	public void setCats(List<Category> cats) {
		this.cats = cats;
	}
	public void addCat(Category cat) {
		cats.add(cat);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", phone=" + phone + ", email=" + email + ", password=" + password
				+ ", userType=" + userType + "]";
	}
	
	
}
