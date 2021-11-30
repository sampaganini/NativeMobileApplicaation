package com.example.fixnow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fixnow.api.ApiProvider;
import com.example.fixnow.api.ApiService;
import com.example.fixnow.api.RetrofitClient;
import com.example.fixnow.models.User;
import com.google.android.gms.common.api.BooleanResult;

import java.io.IOException;

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
                    Bundle bundle = getIntent().getExtras();
                    String type = bundle.getString("type");
                    String login = bundle.getString("login");
                    String password = bundle.getString("password");
                    String name = bundle.getString("name");
                    String surname = bundle.getString("surname");
                    String telephone = bundle.getString("telephone");
                    ApiProvider apiProvider = new ApiProvider();
                    //try {
                      //LiveData<Boolean> success = apiProvider.addUser(new User(login,password,type,name,surname,0,null,null,null,null,null,telephone));
                      //Boolean result = success.getValue();
                      //if(result == false) {
                          //Toast.makeText()
                     // }
                     // else {
                          startActivity(i);
                    //  }
                  //  } catch (IOException e) {
                      //  e.printStackTrace();
                  //  }

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