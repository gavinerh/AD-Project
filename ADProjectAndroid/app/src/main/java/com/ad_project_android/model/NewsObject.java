package com.ad_project_android.model;

public class NewsObject {
    private Source source;
    private String title;
    private String newsUrl;
    private String imageUrl;
    private String description;

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

class Source{
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
