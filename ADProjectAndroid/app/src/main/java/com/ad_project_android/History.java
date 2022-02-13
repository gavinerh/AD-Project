package com.ad_project_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ad_project_android.DataService.NewsService;
import com.ad_project_android.adapters.LikeDislikeAdapter;
import com.ad_project_android.adapters.MyAdapter;
import com.ad_project_android.model.NewsObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class History extends AppCompatActivity {
    LikeDislikeAdapter adapter;
    List<String> like = new ArrayList<>();
    List<String > dislike = new ArrayList<>();
    String tokenString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        populateTokenString();
        getPreference();
    }

    public void postLikeOrDislike(String like, int preference){

        NewsService newsService = getNewsServiceInstance();
        NewsObject newsObject = new NewsObject();
        newsObject.setTitle(like);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String zt = ZonedDateTime.now().format(dateTimeFormatter);
        newsObject.setPublishedAt(zt);

        Call<Void> call = null;
        if(preference == 1){
            call = newsService.postLike(newsObject, tokenString);
        } else if (preference == -1) {
            call = newsService.postDislike(newsObject, tokenString);
        }
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    Toast.makeText(History.this, "Preference is registered", Toast.LENGTH_SHORT).show();
                    List <String> lds = adapter.getLds();
                    lds.remove(like);
                    adapter.setLds(lds);
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(History.this, "Server error, please update preference later",
                            Toast.LENGTH_SHORT).show();}
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(History.this, "Server error, please update preference later",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void populateTokenString(){
        SharedPreferences pref = getSharedPreferences(LoginActivity.USER_CREDENTIAL, MODE_PRIVATE);
        tokenString = pref.getString("token", null);
        if(tokenString == null){
            finish();
            return;
        }
    }
    private NewsService getNewsServiceInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(NewsService.class);
    }

    public void setDialog(String l, int preference){
        // 1. Instantiate an Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("This will permanently remove preference. Are you sure?")
                .setTitle("Warning!");
        // Add the buttons
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                postLikeOrDislike(l,preference);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                adapter.notifyDataSetChanged();
            }
        });
        AlertDialog dialog =  builder.create();
        dialog.show();
    }

    private void getPreference(){
        NewsService newsService = getNewsServiceInstance();

        Call<Map> call = newsService.getPreference(tokenString);
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if(response.code() == 200){
                    like  = (List<String>) response.body().get("likes");
                    dislike = (List<String>) response.body().get("dislikes");
                    setadaptor();
                }
                else{
                    Toast.makeText(History.this,"Server error, Try again later!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                Toast.makeText(History.this,"Server error, Try again later!",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setadaptor(){
        ListView listView = findViewById(R.id.listView);
        if (listView != null) {
            adapter = new LikeDislikeAdapter(this,like,dislike,this);
            listView.setAdapter(adapter);
        }

    }
}