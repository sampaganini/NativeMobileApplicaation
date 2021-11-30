package com.example.fixnow.api;

import com.example.fixnow.models.Login;
import com.example.fixnow.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/register")
    Call<Response> addUser(@Body User user);

    @GET("/authenticate")
    Call<User> loginUser(@Body Login login);

}
