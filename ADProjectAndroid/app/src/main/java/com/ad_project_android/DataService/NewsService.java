package com.ad_project_android.DataService;

import com.ad_project_android.model.Bookmark;
import com.ad_project_android.model.CategoryJson;
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

    //create get method for bookmarked articles
    //call list, bookmark
    @GET("newsapi/bmpreference")
    Call<Map> getBMPreference(@Header("Authorization") String token);

    @GET("newsapi/preference")
    Call<Map> getPreference(@Header("Authorization") String token);

    @GET("newsapi/cats")
    Call<List<String>> getCats();

    @GET("newsapi/category")
    Call<List<CategoryJson>> getUserCats(@Header("Authorization") String token);

    @POST("newsapi/category")
    Call<Void> postUserCats(@Body List<CategoryJson> userprefs,@Header("Authorization") String token);

    @GET("newsapi/bookmark")
    Call<List<Bookmark>> getBookmark(@Header("Authorization") String token);

    @POST("newsapi/like")
    Call<Void> postLike(@Body NewsObject newsObject, @Header("Authorization") String token);

    @POST("newsapi/dislike")
    Call<Void> postDislike(@Body NewsObject newsObject, @Header("Authorization") String token);

  @POST("newsapi/bookmark")
    Call<Void> saveBookmark(@Body NewsObject newsObject, @Header("Authorization") String token);
}
