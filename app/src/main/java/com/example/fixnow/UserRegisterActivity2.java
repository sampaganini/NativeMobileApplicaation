package com.example.fixnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserRegisterActivity2 extends AppCompatActivity {
    private Button register;
    private EditText name;
    private EditText surname;
    private EditText telephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register2);
        getSupportActionBar().hide();
        register = findViewById(R.id.button3);
        name = (EditText)findViewById(R.id.editTextTextPersonName4);
        surname = (EditText)findViewById(R.id.editTextTextPersonName3);
        telephone = (EditText)findViewById(R.id.editTextTextPersonName);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckAllFields()) {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    private boolean CheckAllFields() {
        if (name.length() == 0) {
            name.setError("Name is required");
            return false;
        }

        if (telephone.length() == 0) {
            telephone.setError("Telephone is required");
            return false;
        } else if (telephone.length() < 9) {
            telephone.setError("Wrong number");
            return false;
        }

        if (surname.length() == 0) {
            surname.setError("Surname is required");
            return false;
        }
            // after all validation return true.
            return true;

    }
}