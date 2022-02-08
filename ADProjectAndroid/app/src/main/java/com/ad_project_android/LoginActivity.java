package com.ad_project_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ad_project_android.DataService.UserService;
import com.ad_project_android.model.TokenJWT;
import com.ad_project_android.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    public static final String USER_CREDENTIAL = "user_credential";
    EditText emailInput, passwordInput;
    Button loginBtn, regBtn;
    private long pressTime;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkLogin();

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                checkValidity(email, password);
            }
        });
        regBtn = findViewById(R.id.loginToRegisterBtn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if((pressTime+2000)>System.currentTimeMillis()){
            toast.cancel();
            super.onBackPressed();
            return;
        }
        else{
            toast = Toast.makeText(this,"Press again to exit!",Toast.LENGTH_SHORT);
            toast.show();
        }
        pressTime = System.currentTimeMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // check if there is previous login sessions
        checkLogin();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(toast!=null)toast.cancel();
    }

    private void startMainActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void checkValidity(String email, String password){
        // create retrofit builder
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // create instance of UserService api class
        UserService userService = retrofit.create(UserService.class);
        // passing data from text field into user model
        User user = new User(null, null, email, password);

        // calling method to create a post and pass in model class
        Call<TokenJWT> call = userService.authenticateUser(user);

        // execute post method
        call.enqueue(new Callback<TokenJWT>() {
            @Override
            public void onResponse(Call<TokenJWT> call, Response<TokenJWT> response) {
                if(response.code() == 202){
                    TokenJWT token = response.body();
                    updateLoggedInStatus(token.getEmail(), "Bearer " + token.getToken());
                    startMainActivity();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Invalid email and password", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<TokenJWT> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error in connecting to server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateLoggedInStatus(String email, String token){
        SharedPreferences pref = getSharedPreferences(USER_CREDENTIAL, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("email", email);
        editor.putString("token", token);
        editor.commit();
    }

    private void checkLogin(){
        SharedPreferences pref = getSharedPreferences(USER_CREDENTIAL, MODE_PRIVATE);
        String email = pref.getString("email", "");
        if(email != null && !email.equals("")){
            startMainActivity();
        }
    }
}