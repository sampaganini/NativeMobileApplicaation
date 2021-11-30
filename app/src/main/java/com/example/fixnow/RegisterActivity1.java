package com.example.fixnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity1 extends AppCompatActivity {

    private Button next;
    private EditText login;
    private EditText password;
    private EditText secondPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        getSupportActionBar().hide();
        login = (EditText)findViewById(R.id.editTextTextPersonName4);
        password = (EditText)findViewById(R.id.editTextTextPersonName3);
        secondPassword = (EditText)findViewById(R.id.editTextTextPersonName6);
        next = findViewById(R.id.button3);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckAllFields()) {
                    Bundle bundle = getIntent().getExtras();
                    String type = bundle.getString("type");
                    if(type.equals("SPEC")) {
                        Intent i = new Intent(getApplicationContext(), RegisterActivity2.class);
                        i.putExtra("type", type);
                        startActivity(i);
                    }
                    if(type.equals("SPEC")) {
                        Intent i = new Intent(getApplicationContext(), UserRegisterActivity2.class);
                        i.putExtra("type", type);
                        i.putExtra("login",login.getText().toString());
                        i.putExtra("password",password.getText().toString());
                        startActivity(i);
                    }
                }

            }
        });

    }

    private boolean CheckAllFields() {
        if (login.length() == 0) {
            login.setError("This field is required");
            return false;
        }

        if (password.length() == 0) {
            password.setError("Password is required");
            return false;
        } else if (password.length() < 8) {
            password.setError("Password must be minimum 8 characters");
            return false;
        }

        if (secondPassword.length() == 0) {
            secondPassword.setError("Password is required");
            return false;
        } else if (secondPassword.length() < 8) {
            secondPassword.setError("Password must be minimum 8 characters");
            return false;
        }
        else if(!(secondPassword.getText().toString().equals(password.getText().toString())))
        {
            secondPassword.setError("Passwords must be equals");
            return false;
        }

        // after all validation return true.
        return true;
    }
}

