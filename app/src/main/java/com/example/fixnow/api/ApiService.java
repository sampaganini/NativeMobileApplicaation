package com.example.fixnow.api;

import com.example.fixnow.models.Login;
import com.example.fixnow.models.LoginResponse;
import com.example.fixnow.models.Technican;
import com.example.fixnow.models.User;
import com.example.fixnow.models.findTechRequest;
import com.example.fixnow.models.loginCheck;
import com.example.fixnow.models.opinionRequest;
import com.example.fixnow.models.serviceRequest;
import com.example.fixnow.models.serviceResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/register")
    Call<Void> addUser(@Body User user);

    @POST("/login_exists")
    Call<Void> checkLogin(@Body loginCheck login);

    @POST("/authenticate")
    Call<LoginResponse> loginUser(@Body Login login);

    @POST("/find_technicians")
     Call<List<Technican>> findTechnicans(@Body findTechRequest find);

    @GET("/find_technicians_all")
    Call<List<Technican>> findTechnicansAll();

    @POST("/add_service")
    Call<Void> sendRequest(@Body serviceRequest req);

    @GET("accounts/{id}")
    Call<User> getUser(@Path("id") Long id);

    @GET("/services_get_accepted_user/{id}")
    Call<List<serviceResponse>> getAcceptedUser(@Path("id") Long id);

    @GET("/services_get_accepted_tech/{id}")
    Call<List<serviceResponse>> getAcceptedTech(@Path("id") Long id);

    @GET("/services_get_not_accepted_user/{id}")
    Call<List<serviceResponse>> getUnacceptedUser(@Path("id") Long id);

    @GET("/services_get_not_accepted_tech/{id}")
    Call<List<serviceResponse>> getUnacceptedTech(@Path("id") Long id);

    @GET("/services_reject_service/{id}")
    Call<Void> deleteService(@Path("id") Long id);

    @GET("/services_accept_service/{id}")
    Call<Void> acceptService(@Path("id") Long id);

    @GET("/services_reject_service/{id}")
    Call<Void> rejectService(@Path("id") Long id);

    @GET("/services_complete_service/{id}")
    Call<Void> completeService(@Path("id") Long id);

    @POST("/leave_review")
    Call<Void> sendOpinion(@Body opinionRequest req);

    @GET("/reviews_get_left_for/{id}")
    Call<List<opinionRequest>> getOpinions(@Path("id") Long id);
}
