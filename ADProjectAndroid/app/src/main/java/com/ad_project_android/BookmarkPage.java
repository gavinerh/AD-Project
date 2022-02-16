package com.ad_project_android;

//<<<<<<< Updated upstream
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
//=======
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.SharedPreferences;
//import android.os.Bundle;
//>>>>>>> Stashed changes
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ad_project_android.DataService.NewsService;
import com.ad_project_android.adapters.BookmarkAdapter;
//<<<<<<< Updated upstream
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
//=======
//import com.ad_project_android.adapters.LikeDislikeAdapter;
//import com.ad_project_android.model.Bookmark;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//>>>>>>> Stashed changes

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//<<<<<<< Updated upstream
//BookmarkPage Page
public class BookmarkPage extends AppCompatActivity {
    public static final String BOOKMARK = "BOOKMARK";
    BookmarkAdapter bmadapter;
    ListView listView;
    List<Bookmark> bookmarks= new ArrayList<>();
    String tokenString;
    private ArrayList<File> listOfFiles = new ArrayList<>();
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked);
        populateTokenString();
        getBMPreference();
    }
    @Override
    protected void onDestroy() {
        bookmarks.clear();
        super.onDestroy();

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
                    List <Bookmark> bms = bmadapter.getBookmarks();
                    bms.remove(bkmark);
                    bmadapter.setBookmarks(bms);
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


//=======
//public class BookmarkPage extends AppCompatActivity {
//    String tokenString;
//    List<Bookmark> bookmarks = new ArrayList<>();
//    BookmarkAdapter adapter;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bookmark);
//        populateTokenString();
//        getPreference();
//    }
//>>>>>>> Stashed changes
    private void populateTokenString(){
        SharedPreferences pref = getSharedPreferences(LoginActivity.USER_CREDENTIAL, MODE_PRIVATE);
        tokenString = pref.getString("token", null);
        if(tokenString == null){
            finish();
            return;
        }
    }
//<<<<<<< Updated upstream
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
//=======
//>>>>>>> Stashed changes
    private NewsService getNewsServiceInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(NewsService.class);
    }
//<<<<<<< Updated upstream
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
//=======
//    private void getPreference(){
//        NewsService newsService = getNewsServiceInstance();
//
//        Call<List<Bookmark>> call = newsService.getBookmark(tokenString);
//        call.enqueue(new Callback<List<Bookmark>>() {
//            @Override
//            public void onResponse(Call<List<Bookmark>> call, Response<List<Bookmark>> response) {
//                if(response.code() == 200){
//                    bookmarks=response.body();
//                    setadaptor();
//                }
//                else{
//                    Toast.makeText(BookmarkPage.this,"Server error, Try again later!",Toast.LENGTH_SHORT).show();
//>>>>>>> Stashed changes
                }
            }

            @Override
//<<<<<<< Updated upstream
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(BookmarkPage.this,"(BM) Server error, Try again later!",Toast.LENGTH_SHORT).show();
            }
        });
    }
    //set page using bookmark adapter
    private void setbmadapter(){
        listView = findViewById(R.id.listView);
        if (listView != null) {
            bmadapter = new BookmarkAdapter(
                    this, bookmarks,this);
            listView.setAdapter(bmadapter);
        }
    }
    public void launchwebview(String url){
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(MainActivity.EXTERNAL_URL, url);
        startActivity(intent);
    }

}