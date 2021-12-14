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
import com.example.fixnow.models.Login;
import com.example.fixnow.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
                    Bundle bundle = getIntent().getExtras();
                    String type = bundle.getString("type");
                    String login = bundle.getString("login");
                    String password = bundle.getString("password");
                        Retrofit retrofit = RetrofitClient.getRetrofitClient("http://192.168.100.155:2222");
                        ApiService service = retrofit.create(ApiService.class);
                        Call<Void> call = service.addUser(new User(login,password,type,name.getText().toString(),surname.getText().toString(),null,null,null,null,null,telephone.getText().toString()));
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                int code = response.code();
                                if (response.code() == 200) {
                                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getApplicationContext(),"Problem w rejestracji",Toast.LENGTH_LONG);
                                    System.out.println("!!!!!!!! Request Error code :: " + response.code());
                                }
                            }
                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.out.println("Błąd łączenia");
                                Toast.makeText(getApplicationContext(),"Problem w rejestracji",Toast.LENGTH_LONG);
                            }
                        });
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