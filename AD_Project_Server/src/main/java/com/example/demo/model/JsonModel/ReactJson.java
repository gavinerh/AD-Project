package com.example.demo.model.JsonModel;

// json for communication with react and if possible for android
public class ReactJson {
	private String imageUrl;
	private String webpageUrl;
	private String title;
	private String description;
	private String category;

	public ReactJson(String imageUrl, String webpageUrl, String title, String description, String category) {
		super();
		this.imageUrl = imageUrl;
		this.webpageUrl = webpageUrl;
		this.title = title;
		this.description = description;
		this.category = category;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getWebpageUrl() {
		return webpageUrl;
	}

	public void setWebpageUrl(String webpageUrl) {
		this.webpageUrl = webpageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ReactJson [imageUrl=" + imageUrl + ", webpageUrl=" + webpageUrl + ", title=" + title + ", description="
				+ description + "]";
	}
	
	
}
