package com.ad_project_android.DataService;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

public class Account {
    public static String createJson(String email, String password){
        try{
//            URL url = new URL("http://localhost:8080/account/login");
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("POST");
//            urlConnection.setRequestProperty("Content-Type", "application/json");
//            urlConnection.setDoOutput(true);
//            urlConnection.setDoInput(true);
//            urlConnection.connect();
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("email", email);
            jsonParam.put("password", password);
            return jsonParam.toString();

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
