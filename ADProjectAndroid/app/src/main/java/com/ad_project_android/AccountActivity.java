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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        email = findViewById(R.id.emailInput);
        email.setEnabled(false);
        password = findViewById(R.id.passwordInput);
        phone = findViewById(R.id.phoneInput);
        name = findViewById(R.id.nameInput);
        updateBtn = findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = collateUserInfo();
                UserService userService = getUserServiceInstance();
                Call<User> call = userService.updateUser(user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.code() == 200){
                            finishActivity();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
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

            Call<User> call = userService.getUser(storedEmail);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.code() == 200){
                        // populate the form
                        User user = response.body();
                        populateEditText(user.getEmail(), user.getPassword(), user.getPhone(), user.getName());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Apologies, Server is currently experiencing some unknown error, try again later", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void populateEditText(String emailStr, String passwordStr, String phoneStr, String nameStr){
        email.setText(emailStr); password.setText(passwordStr); phone.setText(phoneStr);
        name.setText(nameStr);
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
        return new User(name.getText().toString(), phone.getText().toString(), email.getText().toString(), password.getText().toString());
    }
}