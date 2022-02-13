package com.ad_project_android.DataService;

import com.ad_project_android.model.NewsObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NewsService {
    @GET("newsapi/news")
    Call<Map> getNews(@Header("Authorization") String token);

    @POST("newsapi/like")
    Call<Void> postLike(@Body NewsObject newsObject, @Header("Authorization") String token);

    @POST("newsapi/dislike")
    Call<Void> postDislike(@Body NewsObject newsObject, @Header("Authorization") String token);

  @POST("newsapi/bookmark/{email}")
    Call<Void> saveBookmark(@Body NewsObject newsObject, @Path("email") String email,
    @Header("Authorization") String token);
}
