package com.ad_project_android.DataService;

import com.ad_project_android.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("account/login")
    Call<User> authenticateUser(@Body User user);

    @POST("account/register")
    Call<User> registerUser(@Body User user);
}
