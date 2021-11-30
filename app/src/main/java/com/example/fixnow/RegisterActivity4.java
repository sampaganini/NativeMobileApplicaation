package com.example.fixnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fixnow.api.ApiService;
import com.example.fixnow.api.RetrofitClient;
import com.example.fixnow.models.User;

import java.io.IOException;

public class RegisterActivity4 extends AppCompatActivity {
    private Button register;
    private EditText description;
    private EditText company;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register4);
        register = findViewById(R.id.button3);
        description = findViewById(R.id.editTextTextPersonName3);
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
                Double longitude = bundle.getDouble("longitude");
                Double latitude = bundle.getDouble("latitude");
                int radius = bundle.getInt("radius");
               // ApiService service = RetrofitClient.getRetrofitClient().create(ApiService.class);
                //try {
                 //   service.addUser(new User(login,password,type,name,surname,radius,longitude,latitude,specialization,description.toString(),company.toString(),telephone)).execute();
               // } catch (IOException e) {
              //      e.printStackTrace();
              //  }
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
    }
}