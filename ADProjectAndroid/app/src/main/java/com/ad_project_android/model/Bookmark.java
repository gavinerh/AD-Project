package com.ad_project_android.model;


import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Objects;

public class Bookmark implements Serializable {
    private String title;
    private String desc;
    private String url;
    private String imageurl;

    public Bookmark(){}
    public Bookmark(String title) {
        this.title=title;
    }
    public Bookmark(String title, String desc, String url, String imageurl){
        this.title = title;
        this.desc=desc;
        this.url=url;
        this.imageurl=imageurl;
    }


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {return desc;}
    public void setDesc(String desc) {this.desc= desc; }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
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
