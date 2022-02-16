package com.ad_project_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.ad_project_android.DataService.NewsService;
import com.ad_project_android.adapters.BookmarkAdapter;
import com.ad_project_android.model.Bookmark;
import com.ad_project_android.model.NewsObject;
import com.google.gson.Gson;

import java.io.File;
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

//BookmarkPage Page
public class BookmarkPage extends AppCompatActivity {
    BookmarkAdapter bmadapter;
    List<Bookmark> bookmarks= new ArrayList<>();
    String tokenString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked);
        populateTokenString();
        getBMPreference();
    }
    //in case cancel unbookmark action, save article
    public void saveBm(Bookmark bkmark, int preference){
        NewsService newsService = getNewsServiceInstance();
        NewsObject newsObject = new NewsObject();
        newsObject.setTitle(bkmark.getTitle());
        Log.d("News Articles:",""+bookmarks.size());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String zt = ZonedDateTime.now().format(dateTimeFormatter);
        newsObject.setPublishedAt(zt);

        Call<Void> call = null;
        if (preference ==2) {
            String email = checkEmail();
            call =newsService.saveBookmark(newsObject, email, tokenString);
        }
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    Toast.makeText(BookmarkPage.this,
                            "(BM) Preference is registered", Toast.LENGTH_SHORT).show();
                    List <Bookmark> bms = bmadapter.getBms();
                    bms.remove(bkmark);
                    bmadapter.setBms(bms);
                    bmadapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(BookmarkPage.this,
                            "(BM) Server error, please try again later",
                            Toast.LENGTH_SHORT).show();}
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(BookmarkPage.this,
                        "(BM) Server error, please try again later",
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
    //condition in case of login error
    private String checkEmail(){
        SharedPreferences pref = getSharedPreferences(LoginActivity.USER_CREDENTIAL, MODE_PRIVATE);
        String email = pref.getString("email", "");
        if(email != null && email.equals("")){
            //toast message that method failed
            Toast.makeText(BookmarkPage.this,
                    "Please register to save preferences", Toast.LENGTH_SHORT).show();
        }
        return email;
    }
    //build page linking to server
    private NewsService getNewsServiceInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(NewsService.class);
    }
    //popup message for confirmation of actions on page
    public void setbmDialog(Bookmark l, int preference) {
        // 1. Instantiate an Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("(BM) This will permanently remove bookmark. Are you sure?")
                .setTitle("Warning!");
        // Add the buttons
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                saveBm(l,preference);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                bmadapter.notifyDataSetChanged();
            }
        });
        AlertDialog dialog =  builder.create();
        dialog.show();
    }
    //call data from Server
    private void getBMPreference(){
        NewsService newsService = getNewsServiceInstance();
        Call<Map> call = newsService.getBMPreference(tokenString);
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if(response.code() == 200){
                    List<Object> bm =(List<Object>) response.body().get("bookmarks");
                    if(bm!=null){
                        bm.stream().forEach(x->{
                            Bookmark b = new Gson().fromJson
                                    (new Gson().toJson(x), Bookmark.class);
                            bookmarks.add(b);
                        });
                    }
                    Log.d("Bookmarks onCreate",""+bookmarks.size());
                    setbmadapter();
                }
                else{
                    Toast.makeText(BookmarkPage.this,"(BM) Server error, Try again later!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(BookmarkPage.this,"(BM) Server error, Try again later!",Toast.LENGTH_SHORT).show();
            }
        });
    }
    //set page using bookmark adapter
    private void setbmadapter(){
        ListView listView = findViewById(R.id.listView);
        if (listView != null) {
            bmadapter = new BookmarkAdapter(
                    this, bookmarks,this);
            listView.setAdapter(bmadapter);
        }
    }
}