package com.ad_project_android.DataService;

import com.ad_project_android.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @POST("account/login")
    Call<User> authenticateUser(@Body User user);

    @POST("account/register")
    Call<User> registerUser(@Body User user);

    @GET("account")
    Call<User> getUser(@Path("email") String email);

    @POST("account/update")
    Call<User> updateUser(@Body User user);
}
