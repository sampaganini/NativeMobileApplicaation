package com.example.fixnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class RegisterActivity2 extends AppCompatActivity {
    private Button register;
    private EditText name;
    private EditText surname;
    private EditText telephone;
    private Spinner specialization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        getSupportActionBar().hide();
        register = findViewById(R.id.button3);
        register = findViewById(R.id.button3);
        name = (EditText)findViewById(R.id.editTextTextPersonName4);
        surname = (EditText)findViewById(R.id.editTextTextPersonName3);
        telephone = (EditText)findViewById(R.id.editTextTextPersonName);
        Spinner specialization = findViewById(R.id.spinner);
        String[] items = new String[]{"Mechanik", "Hydraulik", "Ślusarz"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        specialization.setAdapter(adapter);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckAllFields()) {
                    Bundle bundle = getIntent().getExtras();
                    String type = bundle.getString("type");
                    String login = bundle.getString("login");
                    String password = bundle.getString("password");
                    Intent i = new Intent(getApplicationContext(), RegisterActivity3.class);
                    i.putExtra("type", type);
                    i.putExtra("login",login);
                    i.putExtra("password",password);
                    i.putExtra("name",name.getText().toString());
                    i.putExtra("surname",surname.getText().toString());
                    i.putExtra("telephone",telephone.getText().toString());
                    i.putExtra("specialization",telephone.getText().toString());
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