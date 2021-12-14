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
import com.example.fixnow.models.LoginResponse;
import com.example.fixnow.models.loginCheck;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
                if (CheckAllFields()) {
                    Retrofit retrofit = RetrofitClient.getRetrofitClient("http://192.168.100.155:2222");
                    ApiService service = retrofit.create(ApiService.class);
                    Call<Void> callAsync2 = service.checkLogin(new loginCheck(login.getText().toString()));
                    callAsync2.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.code() == 200) {
                                    Bundle bundle = getIntent().getExtras();
                                    String type = bundle.getString("type");
                                    if (type.equals("TECH")) {
                                        Intent i = new Intent(getApplicationContext(), RegisterActivity2.class);
                                        i.putExtra("type", type);
                                        i.putExtra("login", login.getText().toString());
                                        i.putExtra("password", password.getText().toString());
                                        startActivity(i);
                                    }
                                    if (type.equals("USER")) {
                                        Intent i = new Intent(getApplicationContext(), UserRegisterActivity2.class);
                                        i.putExtra("type", type);
                                        i.putExtra("login", login.getText().toString());
                                        i.putExtra("password", password.getText().toString());
                                        startActivity(i);
                                    }
                                }
                            else {
                                Toast.makeText(getApplicationContext(), "Login exists", Toast.LENGTH_LONG).show();
                                System.out.println("Request Error :: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            System.out.println("!!!!!!" + t.getMessage() + "\n");
                        }
                    });
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

