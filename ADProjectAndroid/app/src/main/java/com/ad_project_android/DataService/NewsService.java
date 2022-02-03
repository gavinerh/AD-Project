package com.ad_project_android.DataService;

import com.ad_project_android.model.NewsObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsService {
    @GET("newsapi")
    Call<List<NewsObject>> getNews();
}
