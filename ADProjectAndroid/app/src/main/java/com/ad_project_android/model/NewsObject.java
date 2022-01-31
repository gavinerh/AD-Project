package com.ad_project_android.model;

public class NewsObject {
    private String title;
    private String newsUrl;
    private String imageUrl;
    private String description;

    public NewsObject(String title, String newsUrl, String imageUrl, String description) {
        this.title = title;
        this.newsUrl = newsUrl;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public NewsObject() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
