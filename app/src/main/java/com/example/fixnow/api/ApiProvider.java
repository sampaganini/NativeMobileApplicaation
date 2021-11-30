package com.example.fixnow.api;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fixnow.models.Login;
import com.example.fixnow.models.User;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
public class ApiProvider {

    public LiveData<Boolean> addUser(@Body User user) throws IOException {
       ApiService service = RetrofitClient.getRetrofitClient().create(ApiService.class);
        Call<Response> callAsync = service.addUser(user);
        final MutableLiveData<Boolean> result = new MutableLiveData<>();
        callAsync.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, Response<Response> response) {
                Response response1 = response.body();
                if (response1.toString().equals("200"))
                {
                    result.postValue(true);
                }
                else
                {
                    result.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                result.postValue(false);
            }
        });
        return  result;
    }

    public LiveData<User> loginUser(String login, String password)
    {
        ApiService service = RetrofitClient.getRetrofitClient().create(ApiService.class);
        Call<User> callAsync = service.loginUser(new Login(login,password));
        final MutableLiveData<User> result = new MutableLiveData<>();
        callAsync.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                // jak nie zadziala to klasa api response
               result.postValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                result.postValue(null);
            }

        });
        return result;
    }

}
