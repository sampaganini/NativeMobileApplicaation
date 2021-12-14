package com.example.fixnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fixnow.api.ApiService;
import com.example.fixnow.api.RetrofitClient;
import com.example.fixnow.models.Login;
import com.example.fixnow.models.serviceRequest;
import com.example.fixnow.models.serviceResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SpecialistActivity extends AppCompatActivity {

    private TableLayout table;
    private Retrofit retrofit;
    private Long id;
    private ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist);
        getSupportActionBar().hide();
        table = findViewById(R.id.table);
        getSupportActionBar().hide();
        List<serviceRequest> services = null;
        BottomNavigationView bottomNav = findViewById(R.id.spec_menu);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        SharedPreferences sharedPref = getSharedPreferences("user_credentials", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", null);
        id = sharedPref.getLong("id", 0);
        retrofit = RetrofitClient.getRetrofitWithToken("http://192.168.100.155:2224", token);
        service = retrofit.create(ApiService.class);

        Call<List<serviceResponse>> call2 = service.getUnacceptedTech(id);
        call2.enqueue(new Callback<List<serviceResponse>>() {
            @Override
            public void onResponse(Call<List<serviceResponse>> call, Response<List<serviceResponse>> response) {
                if (response.code() == 200) {
                    if (response.body().size() == 0 || response.body() == null) {
                        Toast.makeText(getApplicationContext(), "Brak oczekujących zgłoszeń", Toast.LENGTH_LONG).show();
                    } else {
                        setTable(response.body(), false);
                    }
                } else {
                    System.out.println("Problem: " + response.code());
                    Toast.makeText(getApplicationContext(), "Problem z zaladowaniem danych oczekujących: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<serviceResponse>> call, Throwable t) {
                System.out.println("Błąd łączenia" + t.getMessage());
                Toast.makeText(getApplicationContext(), "Problem z wyszukiwaniem", Toast.LENGTH_LONG).show();
            }
        });

        Call<List<serviceResponse>> call = service.getAcceptedTech(id);
        call.enqueue(new Callback<List<serviceResponse>>() {
            @Override
            public void onResponse(Call<List<serviceResponse>> call, Response<List<serviceResponse>> response) {
                if (response.code() == 200) {
                    if (response.body().size() == 0 || response.body() == null) {
                        Toast.makeText(getApplicationContext(), "Brak zaakceptowanych zgłoszeń", Toast.LENGTH_LONG).show();
                    } else {
                        setTable(response.body(), true);
                    }
                } else {
                    System.out.println("Problem" + response.code());
                    Toast.makeText(getApplicationContext(), "Problem z zaladowaniem danych zaakceptowanych: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<serviceResponse>> call, Throwable t) {
                System.out.println("Problem: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Problem z wyszukiwaniem", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setTable(List<serviceResponse> services, boolean is_accepted) {
        services.forEach(info -> {
            if (info.isRejected() != true && info.isCompleted() != true) {
                View tableRow = LayoutInflater.from(this)
                        .inflate(R.layout.spec_row, table, false);
                View separate = LayoutInflater.from(this)
                        .inflate(R.layout.separator, table, false);
                TextView date = tableRow.findViewById(R.id.textView20);
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date2 = new Date(info.getCreated());
                date.setText(date2.toString());
                TextView type = tableRow.findViewById(R.id.textView22);
                Button cancel = tableRow.findViewById(R.id.button16);
                Button end = tableRow.findViewById(R.id.button13);
                Button accept = tableRow.findViewById(R.id.button14);
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Long id = info.getId();
                        acceptService(id,accept,type);
                    }
                });

                if (is_accepted == true) {
                    end.setVisibility(View.VISIBLE);
                    type.setTextColor(Color.GREEN);
                    type.setText("Zaakceptowane");

                } else {
                    type.setTextColor(Color.GRAY);
                    type.setText("Oczekujące");
                }

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Long id = info.getId();
                        deleteService(tableRow, separate, id);
                    }
                });

                end.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Long id = info.getId();
                        deleteService(tableRow, separate, id);
                    }
                });
                TextView desc = tableRow.findViewById(R.id.textView24);
                desc.setText(info.getDescription());

                table.addView(tableRow);
                table.addView(separate);

            }
        });

    }

    private void deleteService(View tableRow, View separate, Long idService) {
        ApiService service = retrofit.create(ApiService.class);
        Call<Void> call = service.deleteService(idService);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    table.removeView(tableRow);
                    table.removeView(separate);
                } else {
                    System.out.println("Problem" + response.code());
                    Toast.makeText(getApplicationContext(), "Problem z anulowaniem usługi: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Problem: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Problem z wyszukiwaniem", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void acceptService(Long idService, Button cancel, TextView type) {
        ApiService service = retrofit.create(ApiService.class);
        Call<Void> call = service.acceptService(idService);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    cancel.setVisibility(View.INVISIBLE);
                    type.setTextColor(Color.GREEN);
                    type.setText("Zaakceptowano");
                    Toast.makeText(getApplicationContext(), "Zaakceptowano" + response.code(), Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("Problem" + response.code());
                    Toast.makeText(getApplicationContext(), "Problem z anulowaniem usługi: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Problem: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Problem z połączeniem", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void dismissService(Long idService, Button end) {
        ApiService service = retrofit.create(ApiService.class);
        Call<Void> call = service.rejectService(idService);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    end.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Problem z odrzuceniem usługi: " + response.code(), Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("Problem" + response.code());
                    Toast.makeText(getApplicationContext(), "Problem z odrzuceniem usługi: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Problem: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Problem z połaczeniem", Toast.LENGTH_LONG).show();
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
                            i = new Intent(getApplicationContext(), SpecialistActivity.class);
                            startActivity(i);
                            return true;
                    }
                    return true;
                }
            };
}