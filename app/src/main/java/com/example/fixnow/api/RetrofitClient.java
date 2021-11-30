package com.example.fixnow.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit getRetrofitClient() {
        OkHttpClient httpClient = new OkHttpClient();
        return new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }

}
