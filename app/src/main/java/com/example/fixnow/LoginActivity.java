package com.example.fixnow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fixnow.api.ApiService;
import com.example.fixnow.api.RetrofitClient;
import com.example.fixnow.models.Login;
import com.example.fixnow.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private EditText log;
    private EditText paswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        login = findViewById(R.id.button3);
        log = findViewById(R.id.editTextTextPersonName4);
        paswd = findViewById(R.id.editTextTextPersonName3);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = RetrofitClient.getRetrofitClient("http://192.168.100.155:2222");
                ApiService service = retrofit.create(ApiService.class);
                Call<LoginResponse> callAsync = service.loginUser(new Login(log.getText().toString(),paswd.getText().toString()));
                callAsync.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            System.out.println("*******" + "User log in");
                            if (response.body().getType().equals("TECH")) {
                                Intent i = new Intent(getApplicationContext(), MenuSpecialistActivity.class);
                                LoginResponse xd = response.body();
                                SharedPreferences sharedPref = getSharedPreferences("user_credentials", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("token", response.body().getToken());
                                editor.apply();
                                editor.putLong("id", response.body().getUserId());
                                editor.apply();
                                startActivity(i);
                            } else if (response.body().getType().equals("USER")) {
                                Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                                String token = response.body().getToken();
                                Long id = response.body().getUserId();
                                SharedPreferences sharedPref = getSharedPreferences("user_credentials", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("token", response.body().getToken());
                                editor.putLong("id", response.body().getUserId());
                                editor.apply();
                                startActivity(i);
                            }
                        } else {
                            System.out.println("Request Error :: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        System.out.println("!!!!!!" + t.getMessage() + "\n");
                    }
                });

            }

        });
    }
}