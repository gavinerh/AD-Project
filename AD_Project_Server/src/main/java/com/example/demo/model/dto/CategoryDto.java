package com.example.demo.model.dto;

public class CategoryDto {
    private Long id;
    private String name;
    private Boolean isChecked;

    public CategoryDto(Long id, String name, Boolean isChecked) {
        super();
        this.id = id;
        this.name = name;
        this.isChecked = isChecked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }
    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public String toString() {
        return "CategoryDto [id=" + id + ", name=" + name + "]";
    }

}
