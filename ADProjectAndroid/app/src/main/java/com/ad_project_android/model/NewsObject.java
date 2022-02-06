package com.ad_project_android.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class NewsObject implements Serializable {
    private String title;
    private String url;
    private String urlToImage;
    private String description;
    private Bitmap bitmap;


//    sourceid: `${article.source.sourceid}`,
//    id: `${article.source.id}`,
//    sourcename: `${article.source.name}`,
//    author: `${article.author}`,
//    title: `${article.title}`,
//    description: `${article.description}`,
//    url: `${article.url}`,
//    imageurl: `${article.urlToImage}`,
//    content: `${article.content}`

    public NewsObject(String title, String newsUrl, String imageUrl, String description) {
        this.title = title;
        this.url = newsUrl;
        this.urlToImage = imageUrl;
        this.description = description;
        bitmap = null;
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
}

