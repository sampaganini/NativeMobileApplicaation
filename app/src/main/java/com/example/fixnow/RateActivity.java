package com.example.fixnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fixnow.api.ApiService;
import com.example.fixnow.api.RetrofitClient;
import com.example.fixnow.models.Login;
import com.example.fixnow.models.User;
import com.example.fixnow.models.opinionRequest;
import com.example.fixnow.models.serviceResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RateActivity extends AppCompatActivity {
    private TextView name;
    private RatingBar rate;
    private EditText opinion;
    private Long user_id;
    private Long tech_id;
    private Button save;
    private ApiService service;
    private Float rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        BottomNavigationView bottomNav = findViewById(R.id.user_menu);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        Bundle bundle = getIntent().getExtras();
        rate = findViewById(R.id.rating);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               rating = rate.getRating();

            }
        });
        user_id = bundle.getLong("user_id");
        tech_id = bundle.getLong("tech_id");
        name = findViewById(R.id.textView15);
        opinion = findViewById(R.id.editTextTextPersonName8);
        SharedPreferences sharedPref = getSharedPreferences("user_credentials", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", null);
        Retrofit retrofit = RetrofitClient.getRetrofitWithToken("http://192.168.100.155:2225",token);
        service = retrofit.create(ApiService.class);
        Call<User> call = service.getUser(tech_id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 200) {
                   name.setText(response.body().getName() + " " + response.body().getLast_name());
                }
                else
                {
                    System.out.println("Problem"+ response.code());
                    Toast.makeText(getApplicationContext(),"Problem z pobraniem imienia: " + response.code(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("Problem: "+t.getMessage());
                Toast.makeText(getApplicationContext(),"Problem z wyszukiwaniem",Toast.LENGTH_LONG).show();
            }
        });

        save = findViewById(R.id.button15);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOpinion();
            }
        });
    }

    private void sendOpinion()
    {
        Float lol = rate.getRating();
        Call<Void> call2 = service.sendOpinion(new opinionRequest(user_id,tech_id,rate.getRating(),opinion.getText().toString()));
        call2.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200) {
                    System.out.println("No Problem: "+ response.code());
                    Toast.makeText(getApplicationContext(),"Opinia wystawiona ",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(i);
                }
                else
                {
                    System.out.println("Problem: "+ response.code());
                    Toast.makeText(getApplicationContext(),"Problem z wystawieniem opinii  " + response.code(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Błąd łączenia"+t.getMessage());
                Toast.makeText(getApplicationContext(),"Problem z połączeniem",Toast.LENGTH_LONG).show();
            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Intent i = null;
                    switch (item.getItemId()) {
                        case R.id.item1:
                            i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);
                            return true;

                        case R.id.item2:
                            i = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(i);
                            return true;

                        case R.id.item3:
                            i = new Intent(getApplicationContext(), SearchActivity.class);
                            startActivity(i);
                            return true;

                        case R.id.item4:
                            i = new Intent(getApplicationContext(), UserActivity.class);
                            startActivity(i);
                            return true;

                    }
                    return true;
                }
            };
}