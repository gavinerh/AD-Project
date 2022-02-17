package com.example.demo.model.JsonModel;

public class CategoryJson {
	private String name;
	private boolean isChecked;
	public CategoryJson(String name, boolean isChecked) {
		super();
		this.name = name;
		this.isChecked = isChecked;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	@Override
	public String toString() {
		return "CategoryJson [name=" + name + ", isChecked=" + isChecked + "]";
	}
	
	
}
