package com.ad_project_android.DataService;

import com.ad_project_android.model.NewsObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NewsService {
    @GET("newsapi/news")
    Call<List<NewsObject>> getNews();

    @POST("newsapi/like")
    Call<Void> postLike(@Body NewsObject newsObject);

    @POST("newsapi/dislike")
    Call<Void> postDislike(@Body NewsObject newsObject);
}
