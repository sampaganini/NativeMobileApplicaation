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
import com.example.fixnow.models.Technican;
import com.example.fixnow.models.findTechRequest;
import com.example.fixnow.models.serviceRequest;
import com.example.fixnow.models.serviceResponse;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserActivity extends AppCompatActivity {

    private TableLayout table;
    private Retrofit retrofit;
    private Long id;
    private ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        table = findViewById(R.id.table);
        getSupportActionBar().hide();
        List<serviceRequest> services = null;
        BottomNavigationView bottomNav = findViewById(R.id.user_menu);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        SharedPreferences sharedPref = getSharedPreferences("user_credentials", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", null);
        id = sharedPref.getLong("id", 0);
        retrofit = RetrofitClient.getRetrofitWithToken("http://192.168.100.155:2224",token);
        service = retrofit.create(ApiService.class);
      Call<List<serviceResponse>> call = service.getAcceptedUser(id);
        call.enqueue(new Callback<List<serviceResponse>>() {
            @Override
            public void onResponse(Call<List<serviceResponse>> call, Response<List<serviceResponse>> response) {
                if(response.code() == 200) {
                    if (response.body().size() == 0 || response.body() == null){
                        Toast.makeText(getApplicationContext(),"Brak zaakceptowanych zgłoszeń",Toast.LENGTH_LONG).show();
                    }
                    else{
                        setTable(response.body(),true);
                    }
                }
                else
                {
                    System.out.println("Problem"+ response.code());
                    Toast.makeText(getApplicationContext(),"Problem z zaladowaniem danych zaakceptowanych: " + response.code(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<serviceResponse>> call, Throwable t) {
                System.out.println("Problem: "+t.getMessage());
                Toast.makeText(getApplicationContext(),"Problem z wyszukiwaniem",Toast.LENGTH_LONG).show();
            }
        });

        Call<List<serviceResponse>> call2 = service.getUnacceptedUser(id);
        call2.enqueue(new Callback<List<serviceResponse>>() {
            @Override
            public void onResponse(Call<List<serviceResponse>> call, Response<List<serviceResponse>> response) {
                if(response.code() == 200) {
                    if (response.body().size() == 0 || response.body() == null) {
                        Toast.makeText(getApplicationContext(), "Brak oczekujących zgłoszeń", Toast.LENGTH_LONG).show();
                    } else {
                        setTable(response.body(), false);
                    }
                }
                else
                {
                    System.out.println("Problem: "+ response.code());
                    Toast.makeText(getApplicationContext(),"Problem z zaladowaniem danych oczekujących: " + response.code(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<serviceResponse>> call, Throwable t) {
                System.out.println("Błąd łączenia"+t.getMessage());
                Toast.makeText(getApplicationContext(),"Problem z wyszukiwaniem",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setUnaccepted()
    {
        Call<List<serviceResponse>> call2 = service.getUnacceptedUser(id);
        call2.enqueue(new Callback<List<serviceResponse>>() {
            @Override
            public void onResponse(Call<List<serviceResponse>> call, Response<List<serviceResponse>> response) {
                if(response.code() == 200) {
                    if (response.body().size() == 0 || response.body() == null) {
                        Toast.makeText(getApplicationContext(), "Brak oczekujących zgłoszeń", Toast.LENGTH_LONG).show();
                    } else {
                        setTable(response.body(), false);
                    }
                }
                else
                {
                    System.out.println("Problem: "+ response.code());
                    Toast.makeText(getApplicationContext(),"Problem z zaladowaniem danych oczekujących: " + response.code(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<serviceResponse>> call, Throwable t) {
                System.out.println("Błąd łączenia"+t.getMessage());
                Toast.makeText(getApplicationContext(),"Problem z wyszukiwaniem",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setTable(List<serviceResponse> services, boolean is_accepted)
    {
        services.forEach(info -> {
            if(info.isRejected()!= true && info.isCompleted() != true) {
                View tableRow = LayoutInflater.from(this)
                        .inflate(R.layout.user_row, table, false);
                View separate = LayoutInflater.from(this)
                        .inflate(R.layout.separator, table, false);
                TextView date = tableRow.findViewById(R.id.textView20);
                Locale loc = new Locale("pl", "PL");
                DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("pl"));
                SimpleDateFormat sdfForParsing = new SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH);
                Date date2 = new Date(info.getCreated());
                date.setText(df.format(date2));
                TextView type = tableRow.findViewById(R.id.textView22);
                Button end = tableRow.findViewById(R.id.button13);
                if (is_accepted == true) {
                    type.setTextColor(Color.GREEN);
                    type.setText("Zaakceptowane");
                    end.setVisibility(View.VISIBLE);

                } else {
                    type.setTextColor(Color.GRAY);
                    type.setText("Oczekujące");
                }
                TextView desc = tableRow.findViewById(R.id.textView24);
                desc.setText(info.getDescription());
                Button cancel = tableRow.findViewById(R.id.button16);
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
                        completeService(tableRow, separate, id, info.getTechnicianId(),info.getUserId());
                    }
                });
                table.addView(tableRow);
                table.addView(separate);

            }
        });

    }

    private void deleteService(View tableRow, View separate,Long idService)
    {
        ApiService service = retrofit.create(ApiService.class);
        Call<Void> call = service.deleteService(idService);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200) {
                    table.removeView(tableRow);
                    table.removeView(separate);
                }
                else
                {
                    System.out.println("Problem"+ response.code());
                    Toast.makeText(getApplicationContext(),"Problem z anulowaniem usługi: " + response.code(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Problem: "+t.getMessage());
                Toast.makeText(getApplicationContext(),"Problem z wyszukiwaniem",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void completeService(View tableRow, View separate,Long idService,Long techId,Long uId)
    {
        ApiService service = retrofit.create(ApiService.class);
        Call<Void> call = service.completeService(idService);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200) {
                    table.removeView(tableRow);
                    table.removeView(separate);
                    Intent i = new Intent(getApplicationContext(), RateActivity.class);
                    i.putExtra("user_id",uId);
                    i.putExtra("tech_id",techId);
                    startActivity(i);
                }
                else
                {
                    System.out.println("Problem"+ response.code());
                    Toast.makeText(getApplicationContext(),"Problem z zakończeniem usługi: " + response.code(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
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