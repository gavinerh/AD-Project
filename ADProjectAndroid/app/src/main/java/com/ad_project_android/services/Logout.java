package com.ad_project_android.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.ad_project_android.LoginActivity;

public class Logout {
    public static void logout(Context context){
        SharedPreferences pref = context.getSharedPreferences(LoginActivity.USER_CREDENTIAL, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        Toast.makeText(context,"Log in Expired! Please Log In Again!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("finish", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
        context.startActivity(intent);
    }
}
