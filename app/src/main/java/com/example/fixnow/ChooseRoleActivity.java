package com.example.fixnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseRoleActivity extends AppCompatActivity {

    private Button specialist;
    private Button looking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);
        getSupportActionBar().hide();
        looking = findViewById(R.id.button7);
        specialist= findViewById(R.id.button6);
        Intent intent = new Intent(getApplicationContext(),RegisterActivity1.class);
        looking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type", "USER");
                startActivity(intent);
            }
        });
        specialist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type", "SPEC");
                startActivity(intent);
            }
        });
    }
}