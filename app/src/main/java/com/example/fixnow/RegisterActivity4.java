package com.example.fixnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fixnow.api.ApiService;
import com.example.fixnow.api.RetrofitClient;
import com.example.fixnow.models.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity4 extends AppCompatActivity {
    private Button register;
    private EditText description;
    private EditText company;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register4);
        getSupportActionBar().hide();
        register = findViewById(R.id.button3);
        description = findViewById(R.id.editTextTextPersonName8);
        company = findViewById(R.id.editTextTextPersonName4);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();
                String type = bundle.getString("type");
                String login = bundle.getString("login");
                String password = bundle.getString("password");
                String name = bundle.getString("name");
                String surname = bundle.getString("surname");
                String telephone = bundle.getString("telephone");
                String specialization = bundle.getString("specialization");
                Float longitude = bundle.getFloat("longitude");
                Float latitude = bundle.getFloat("latitude");
                Float radius = bundle.getFloat("radius");
                Long spec = null;
                if(specialization.equals("Hydraulik"))
                {
                   spec = Long.valueOf(1);
                }
                if(specialization.equals("Mechanik"))
                {
                    spec = Long.valueOf(2);
                }
                if(specialization.equals("Stolarz"))
                {
                    spec = Long.valueOf(3);
                }
                if(specialization.equals("Elektryk"))
                {
                    spec = Long.valueOf(1);
                }

                Retrofit retrofit = RetrofitClient.getRetrofitClient("http://192.168.100.155:2222");
                ApiService service = retrofit.create(ApiService.class);
                Call<Void> call = service.addUser(new User(login,password,type,name,surname,radius,longitude,latitude,spec,description.getText().toString(),telephone));
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        int code = response.code();
                        if (response.code() == 200) {
                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(),"Problem w rejestracji",Toast.LENGTH_LONG).show();
                            System.out.println("!!!!!!!! Request Error code :: " + response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.println("Błąd łączenia"+t.getMessage());
                        Toast.makeText(getApplicationContext(),"Problem w rejestracji",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}