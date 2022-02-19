package com.ad_project_android.model;


import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Objects;

public class Bookmark implements Serializable {
    private String title;
    private String url;
    private String imageurl;
    private Bitmap bitmap;

    public Bookmark(){}
    public Bookmark(String title) {
        this.title=title;
    }
    public Bookmark(String title, String url,
                    String imageurl, Bitmap bitmap){
        this.title = title;
        this.url=url;
        this.imageurl=imageurl;
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

    public String getImageurl() {
        return imageurl;
    }
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
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
        Bookmark another = (Bookmark) obj;
        if(obj == null) return false;
        if(title.equals(another.title)){
            return true;
        }
        return false;
    }
}
