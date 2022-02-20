package com.example.demo.model.JsonModel;

public class CategoryJson {
	private String name;
	private boolean select;
	public CategoryJson(String name, boolean isChecked) {
		super();
		this.name = name;
		this.select = isChecked;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isSelect() {
		return select;
	}
	public void setSelect(boolean select) {
		this.select = select;
	}
	@Override
	public String toString() {
		return "CategoryJson [name=" + name + ", isChecked=" + select + "]";
	}
	
	
}
