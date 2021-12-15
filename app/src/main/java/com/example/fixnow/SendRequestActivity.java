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
import android.widget.TextView;
import android.widget.Toast;

import com.example.fixnow.api.ApiService;
import com.example.fixnow.api.RetrofitClient;
import com.example.fixnow.models.Login;
import com.example.fixnow.models.serviceRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SendRequestActivity extends AppCompatActivity {

    private TextView techName;
    private EditText desc;
    private EditText budget;
    private Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_request_activity);
        getSupportActionBar().hide();
        techName = findViewById(R.id.textView15);
        desc = findViewById(R.id.editTextTextPersonName8);
        BottomNavigationView bottomNav = findViewById(R.id.user_menu);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        send = findViewById(R.id.button15);
        Bundle bundle = getIntent().getExtras();
        Long id = bundle.getLong("id");
        String name2 = bundle.getString("name");
        String surname = bundle.getString("surname");
        techName.setText(name2 + " " +surname);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                Bundle bundle = getIntent().getExtras();
                Long id = bundle.getLong("id");
                SharedPreferences sharedPref = getSharedPreferences("user_credentials", Context.MODE_PRIVATE);
                String token  = sharedPref.getString("token", null);
                Long id_user  = sharedPref.getLong("id", 0);
                BottomNavigationView bottomNav = findViewById(R.id.user_menu);
                bottomNav.setOnNavigationItemSelectedListener(navListener);
                Retrofit retrofit = RetrofitClient.getRetrofitWithToken("http://192.168.100.155:2224",token);
                ApiService service = retrofit.create(ApiService.class);
                Date d = new Date();
                Call<Void> call = service.sendRequest(new serviceRequest(id_user,id,System.currentTimeMillis(),desc.getText().toString()));
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        int code = response.code();
                        if (response.code() == 200) {
                            Toast.makeText(getApplicationContext(),"Wysłano zgłoszenie",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(),MenuActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(),"Problem z wyslaniem zgłoszenia" +
                                    "",Toast.LENGTH_LONG).show();
                            System.out.println("!!!!!!!! Request Error code :: " + response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.println("Błąd łączenia"+t.getMessage());
                        Toast.makeText(getApplicationContext(),"Problem z wyslaniem zgłoszenia",Toast.LENGTH_LONG).show();
                    }
                });
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