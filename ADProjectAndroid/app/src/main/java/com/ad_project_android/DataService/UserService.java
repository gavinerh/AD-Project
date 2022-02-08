package com.ad_project_android.DataService;

import com.ad_project_android.model.TokenJWT;
import com.ad_project_android.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @POST("account/authenticate")
    Call<TokenJWT> authenticateUser(@Body User user);

    @POST("account/register")
    Call<User> registerUser(@Body User user);

    @GET("account/{email}")
    Call<User> getUser(@Path("email") String email, @Header("Authorization") String token);

    @PUT("account/update")
    Call<User> updateUser(@Body User user, @Header("Authorization") String token);
}
