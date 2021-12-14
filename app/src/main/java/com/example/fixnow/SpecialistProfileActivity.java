package com.example.fixnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fixnow.api.ApiService;
import com.example.fixnow.api.RetrofitClient;
import com.example.fixnow.models.Login;
import com.example.fixnow.models.Technican;
import com.example.fixnow.models.findTechRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SpecialistProfileActivity extends AppCompatActivity {
    private TextView desc;
    private TextView name;
    private Button opinions;
    private TextView rate;
    private Button phone;
    private Button request;
    private TextView type;
    private RatingBar rate_bar;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_specialist_profile);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        desc = findViewById(R.id.textView13);
        name = findViewById(R.id.textView12);
        opinions= findViewById(R.id.button12);
        rate = findViewById(R.id.textView10);
        request = findViewById(R.id.button11);
        type = findViewById(R.id.textView9);
        rate_bar = findViewById(R.id.rating);
        image = findViewById(R.id.imageView5);
        request = findViewById(R.id.button11);
        opinions = findViewById(R.id.button12);
        BottomNavigationView bottomNav = findViewById(R.id.user_menu);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        Bundle bundle = getIntent().getExtras();
        String description =  bundle.getString("desc");
        desc.setText(description);
        Long type_id = bundle.getLong("service");
        if(type_id == 1)
        {
            type.setText("Hydraulik");
        }
        if(type_id == 2)
        {
            type.setText("Mechanik");
        }
        if(type_id == 3)
        {
            type.setText("Stolarz");
        }
        if(type_id == 4)
        {
            type.setText("Elektryk");
        }
        Float rating = bundle.getFloat("rating");
        rate_bar.setRating(rating);
        rate.setText(rating.toString());
        String name2 = bundle.getString("name");
        String surname = bundle.getString("last_name");
        name.setText(name2 +" " +surname);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SendRequestActivity.class);
                Bundle bundle = getIntent().getExtras();
                Long id = bundle.getLong("account_id");
                i.putExtra("id",id);
                i.putExtra("name",name2);
                i.putExtra("surname",surname);
                startActivity(i);
            }
        });

        opinions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), OpinionsActivity.class);
                Bundle bundle = getIntent().getExtras();
                Long id = bundle.getLong("account_id");
                i.putExtra("id",id);
                startActivity(i);
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
                            i = new Intent(getApplicationContext(), Login.class);
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