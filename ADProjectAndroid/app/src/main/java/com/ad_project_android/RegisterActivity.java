package com.ad_project_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ad_project_android.DataService.UserService;
import com.ad_project_android.model.User;
import com.ad_project_android.services.Logout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    EditText emailInput, passwordInput, checkPasswordInput;
    Button regBtn;
    List<String> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();
        categories = (List<String>) intent.getSerializableExtra("categories");

//        nameInput = findViewById(R.id.reg_nameinput);
        emailInput = findViewById(R.id.reg_emailinput);
        passwordInput = findViewById(R.id.reg_passwordinput);
        checkPasswordInput = findViewById(R.id.reg_checkpassword);

        regBtn = findViewById(R.id.registerBtn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String name = nameInput.getText().toString();
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                String checkPassword = checkPasswordInput.getText().toString();

                if(checkFields(email, password, checkPassword)) {
                    registerUser(email, password);
                }
            }
        });
    }

    private boolean checkFields(String email, String password, String checkPassword) {
        if(isEmpty(emailInput) || isEmpty(passwordInput) || isEmpty(checkPasswordInput)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!password.equals(checkPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void registerUser(String email, String password){
        // create retrofit builder
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // create instance of UserService api class
        UserService userService = retrofit.create(UserService.class);
        // passing data from text field into user model
        User user = new User(null, null, email, password);
        Map register = new HashMap();
        register.put("user",user);
        register.put("categories",categories);
        // calling method to create a post and pass in model class
        Call<Void> call = userService.registerUser(register);

        // execute post method
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 201){
                    Toast.makeText(getApplicationContext(), "Sign up successful!", Toast.LENGTH_SHORT).show();
                    backToLogin();
                    return;
                }
                else if(response.code()==401){
                    Logout.logout(RegisterActivity.this);
                }
                else if(response.code()==409)Toast.makeText(getApplicationContext(), "Email is already registered", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error in connecting to server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void backToLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }


    boolean isEmpty(@NonNull EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}