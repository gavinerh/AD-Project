package com.ad_project_android.model;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class LikeDislike implements Serializable {
    private String title;
    private String desc;
    private String url;
    private String urlToImage;
    private Bitmap bitmap;

    public LikeDislike(String title, String url, String urlToImage, Bitmap bitmap) {
        this.title = title;
        this.url = url;
        this.urlToImage = urlToImage;
        this.bitmap = bitmap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result +
                title.hashCode();
        return result;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        LikeDislike another = (LikeDislike) obj;
        if(obj == null) return false;
        if(title.equals(another.title)){
            return true;
        }
        return false;
    }
}
