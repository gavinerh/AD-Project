package com.ad_project_android.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class NewsObject implements Serializable {
    private String title;
    private String url;
    private String urlToImage;
    private String description;
    private Bitmap bitmap;
    private String publishedAt;
    private Source source;

    public NewsObject(String title, String newsUrl, String imageUrl, String description, String publishedAt, Source source) {
        this.title = title;
        this.url = newsUrl;
        this.urlToImage = imageUrl;
        this.description = description;
        bitmap = null;
        this.publishedAt = publishedAt;
        this.source = source;
    }

    public NewsObject() {
    }

    public String getTitle() {
        return title;
    }

    public String getNewsUrl() {
        return url;
    }

    public void setNewsUrl(String newsUrl) {
        this.url = newsUrl;
    }

    public String getImageUrl() {
        return urlToImage;
    }

    public void setImageUrl(String imageUrl) {
        this.urlToImage = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getPublishedAt() {
        //need to put in nicer string
        //or calc last published
        return publishedAt; }

    public void setPublishedAt(String publishedAt) { this.publishedAt = publishedAt; }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }
}

