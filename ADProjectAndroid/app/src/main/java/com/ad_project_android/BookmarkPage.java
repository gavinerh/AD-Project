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
//=======
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.SharedPreferences;
//import android.os.Bundle;
//>>>>>>> Stashed changes
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ad_project_android.DataService.NewsService;
import com.ad_project_android.adapters.BookmarkAdapter;
//<<<<<<< Updated upstream
import com.ad_project_android.model.Bookmark;
import com.ad_project_android.model.NewsObject;
import com.ad_project_android.services.Logout;
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

//BookmarkPage Page
public class BookmarkPage extends AppCompatActivity {
    public static final String BOOK_MARK = "BOOKMARK";
    private ArrayList<File> listOfBMFiles = new ArrayList<>();
    private List<Bookmark> bms = new ArrayList<>();
    private ArrayList<Bookmark> dynamicbms = new ArrayList<>();

    BookmarkAdapter  bmadapter;
    TextView bmtxt;
    String tokenString;

    private BroadcastReceiver bmreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            File bmfile = (File) intent.getSerializableExtra("BMFILE");
            Bookmark bm = (Bookmark) intent.getSerializableExtra(BOOK_MARK);
            if(bmfile!=null){
            Bitmap bitmap = BitmapFactory.decodeFile(bmfile.getAbsolutePath());
            bm.setBitmap(bitmap);}
            if(bm!=null){
            dynamicbms.add(bm);
            bmadapter.notifyDataSetChanged();
            if(bms.size()==0){bmtxt.setVisibility(View.VISIBLE);}
            }
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(bmreceiver, new IntentFilter(MyNewsService.NOTIFICATION));
        Log.d("Bookmarks onResume",""+bms.size());
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(bmreceiver);
        Log.d("Bookmarks onPause",""+bms.size());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked);
        bmtxt = findViewById(R.id.bm_blankMsg);
        bmtxt.setVisibility(View.GONE);
        populateTokenString();
        getBMPreference();
    }
    @Override
    protected void onDestroy() {
        bms.clear();
        super.onDestroy();

    }

    //in case cancel unbookmark action, save article
    public void saveBm(Bookmark bkmark, int preference){
        NewsService newsService = getNewsServiceInstance();
        NewsObject newsObject = new NewsObject();
        newsObject.setTitle(bkmark.getTitle());

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String zt = ZonedDateTime.now().format(dateTimeFormatter);
        newsObject.setPublishedAt(zt);

        Call<Void> call = null;
        if (preference ==2) {
            String email = checkEmail();
            call =newsService.saveBookmark(newsObject,tokenString);
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
                    if(bms.size()==0){bmtxt.setVisibility(View.VISIBLE);}
                }
                else{
                    Logout.logout(BookmarkPage.this);
                }

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
        Call<List<Bookmark>> call = newsService.getBMPreference(tokenString);
        call.enqueue(new Callback<List<Bookmark>>() {
            @Override
            public void onResponse(Call<List<Bookmark>> call, Response<List<Bookmark>> response) {
                if(response.code() == 200){
                    List<Bookmark> bm = response.body();
                    if(bm!=null){
                        bm.stream().forEach(x->{
                            Bookmark b = new Gson().fromJson
                                    (new Gson().toJson(x), Bookmark.class);
                            bms.add(b);
                        });
                    }
                    Log.d("Bookmarks onCreate",""+bms.size());
                    if(bms.size()>0){
                        initFilesList();
                        setBMadapter();
                        populateBMAdapter();
                    }
                    else{
                        bmtxt.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    Logout.logout(BookmarkPage.this);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(BookmarkPage.this,"(BM) Server error, Try again later!",Toast.LENGTH_SHORT).show();
            }
        });
    }
    //set page using bookmark adapter
    private void setBMadapter() {
//      private void setBMadapter(List<Bookmark> bookMarks) {
        ListView listView = findViewById(R.id.listView);
        if (listView != null) {
            bmadapter = new BookmarkAdapter(
                    this, dynamicbms,this);
            bmadapter.setBms(dynamicbms);
            listView.setAdapter(bmadapter);
        }
    }

    public void launchwebview(String url){
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(MainActivity.EXTERNAL_URL, url);
        startActivity(intent);
    }
    private void populateBMAdapter(){
        for(int i=0; i<bms.size(); i++){
            startService(bms.get(i), listOfBMFiles.get(i));
        }
    }
    private void startService(Bookmark bookMark, File bmfile){
        Intent intent = new Intent(getApplicationContext(), MyNewsService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BOOK_MARK, bookMark);
        bundle.putSerializable("BMFILE", bmfile);
        intent.putExtra(BOOK_MARK, bundle);
        startService(intent);
    }
    private void initFilesList(){
        for(int i=0; i<bms.size(); i++){
            String filename = "bm_image";
            filename += String.format("%s", i);
            File f = initFile(filename);
            listOfBMFiles.add(f);
        }
    }
    private File initFile(String filename){
        File f = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(f, filename);
    }

}