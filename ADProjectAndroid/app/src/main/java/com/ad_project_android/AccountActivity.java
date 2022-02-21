package com.ad_project_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ad_project_android.DataService.UserService;
import com.ad_project_android.model.User;
import com.ad_project_android.services.Logout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText phone;
    EditText name;
    Button updateBtn;
    private String tokenString = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        populateTokenString();
        email = findViewById(R.id.emailInput);
        email.setEnabled(false);
        password = findViewById(R.id.passwordInput);
        phone = findViewById(R.id.phoneInput);
        name = findViewById(R.id.nameInput);
        updateBtn = findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(collateUserInfo() == null) {
                    return;
                }
                User user = collateUserInfo();
                UserService userService = getUserServiceInstance();
                Call<User> call = userService.updateUser(user, tokenString);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.code() == 200){
                            Toast.makeText(AccountActivity.this,"Account Update Successful",Toast.LENGTH_SHORT);
                            finishActivity();
                        }
                        else{
                            Logout.logout(AccountActivity.this);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Server error, please update account later", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        retrieveInfoFromServer();
    }

    private void finishActivity(){
        Intent intent = new Intent(getApplicationContext(), ViewSettings.class);
        startActivity(intent);
        finish();
    }

    private void retrieveInfoFromServer(){
        String storedEmail = getFromSharedPreference();
        if(!storedEmail.equals("")){
            // create instance of UserService api class
            UserService userService = getUserServiceInstance();

            Call<User> call = userService.getUser(storedEmail, tokenString);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.code() == 200){
                        // populate the form
                        User user = response.body();
                        populateEditText(user.getEmail(), user.getPhone(), user.getName());
                    }
                    else{
                        Logout.logout(AccountActivity.this);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Server error, please update account later", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void populateEditText(String emailStr, String phoneStr, String nameStr){
        email.setText(emailStr); phone.setText(phoneStr); name.setText(nameStr);
    }

    private String getFromSharedPreference(){
        SharedPreferences pref = getSharedPreferences(LoginActivity.USER_CREDENTIAL, MODE_PRIVATE);
        return pref.getString("email", "");
    }

    private UserService getUserServiceInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(UserService.class);
    }

    private User collateUserInfo(){
        if(password.getText().toString().equals("") || password.getText() == null) {
            Toast.makeText(getApplicationContext(), "Please fill in password", Toast.LENGTH_SHORT).show();
            return null;
        } if(password.getText().length() < 3) {
            Toast.makeText(getApplicationContext(), "Password should be at least 3 characters", Toast.LENGTH_SHORT).show();
            return null;
        }
        else {
            return new User(name.getText().toString(), phone.getText().toString(), email.getText().toString(), password.getText().toString());

        }
    }

    private void populateTokenString(){
        SharedPreferences pref = getSharedPreferences(LoginActivity.USER_CREDENTIAL, MODE_PRIVATE);
        tokenString = pref.getString("token", null);
    }
}