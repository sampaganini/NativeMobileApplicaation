package com.example.fixnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fixnow.api.ApiService;
import com.example.fixnow.api.RetrofitClient;
import com.example.fixnow.models.Login;
import com.example.fixnow.models.User;
import com.example.fixnow.models.opinionRequest;
import com.example.fixnow.models.serviceResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OpinionsActivity extends AppCompatActivity {
    private TableLayout table;
    private Retrofit retrofit;
    private Long id;
    private ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinions);
        getSupportActionBar().hide();
        BottomNavigationView bottomNav = findViewById(R.id.user_menu);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        table = findViewById(R.id.table);
        SharedPreferences sharedPref = getSharedPreferences("user_credentials", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", null);
        retrofit = RetrofitClient.getRetrofitWithToken("http://192.168.100.155:2225",token);
        Bundle bundle = getIntent().getExtras();
        Long id = bundle.getLong("id");
        service = retrofit.create(ApiService.class);
        Call<List<opinionRequest>> call2 = service.getOpinions(id);
        call2.enqueue(new Callback<List<opinionRequest>>() {
            @Override
            public void onResponse(Call<List<opinionRequest>> call, Response<List<opinionRequest>> response) {
                if(response.code() == 200) {
                    if (response.body().size() == 0 || response.body() == null) {
                        Toast.makeText(getApplicationContext(), "Brak opinii", Toast.LENGTH_LONG).show();
                    } else {
                        setTable(response.body());
                    }
                }
                else
                {
                    System.out.println("Problem: "+ response.code());
                    Toast.makeText(getApplicationContext(),"Problem z zaladowaniem opinii: " + response.code(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<opinionRequest>> call, Throwable t) {
                System.out.println("Błąd łączenia"+t.getMessage());
                Toast.makeText(getApplicationContext(),"Problem z polaczeniem",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setTable(List<opinionRequest> opinions)
    {
        opinions.forEach(info -> {
                View tableRow = LayoutInflater.from(this)
                        .inflate(R.layout.opinion_row, table, false);
                View separate = LayoutInflater.from(this)
                        .inflate(R.layout.separator, table, false);
                TextView name = tableRow.findViewById(R.id.textView20);
                RatingBar rate = tableRow.findViewById(R.id.ratingBar);
                TextView desc = tableRow.findViewById(R.id.textView24);
                TextView rating = tableRow.findViewById(R.id.textView10);
                rating.setText(info.getStarRating().toString());
                setName(name,info.getLeftBy());
                rate.setRating(info.getStarRating());
                desc.setText(info.getContent());
                table.addView(tableRow);
                table.addView(separate);

        });

    }

    private void setName(TextView name, Long id)
    {
        Call<User> call = service.getUser(id);
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