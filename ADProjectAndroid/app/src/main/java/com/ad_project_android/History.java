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
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ad_project_android.DataService.NewsService;
import com.ad_project_android.adapters.LikeDislikeAdapter;
import com.ad_project_android.model.Bookmark;
import com.ad_project_android.model.LikeDislike;
import com.ad_project_android.model.NewsObject;
import com.google.gson.Gson;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class History extends AppCompatActivity {
    public static final String LIKE_DISLIKE = "LD";
    LikeDislikeAdapter adapter;
    List<LikeDislike> like = new ArrayList<>();
    List<LikeDislike> dislike = new ArrayList<>();
    List<LikeDislike> lds = new ArrayList<>();
    List<LikeDislike> dynlds = new ArrayList<>();
    private ArrayList<File> listOfFiles = new ArrayList<>();
    TextView textView;
    TextView ldtitle;
    String tokenString;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            File file = (File) intent.getSerializableExtra("LDFILE");
            LikeDislike ld = (LikeDislike) intent.getSerializableExtra(LIKE_DISLIKE);
            if(file!=null) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                ld.setBitmap(bitmap);}
            if(ld!=null){
            dynlds.add(ld);
            adapter.notifyDataSetChanged();}
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(MyNewsService.NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        textView = findViewById(R.id.txt);
        textView.setVisibility(View.GONE);
        ldtitle = findViewById(R.id.likedislikeTitle);
        populateTokenString();
        getPreference();
    }
    @Override
    protected void onDestroy() {
        lds.clear();
        dynlds.clear();
        super.onDestroy();

    }

    public void postLikeOrDislike(LikeDislike like, int preference){

        NewsService newsService = getNewsServiceInstance();
        NewsObject newsObject = new NewsObject();
        newsObject.setTitle(like.getTitle());

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
                    List<LikeDislike> lds = adapter.getLds();
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

    public void setDialog(LikeDislike l, int preference){
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
                    List<Object> l  = (List<Object>) response.body().get("likes");
                    List<Object> d = (List<Object>) response.body().get("dislikes");
                    if(l!=null){
                        l.stream().forEach(x-> {
                            LikeDislike ld = new Gson().fromJson(new Gson().toJson(x),LikeDislike.class);
                            like.add(ld);
                        });
                    }
                    if(d!=null){
                        d.stream().forEach(x->{
                            LikeDislike ld1 = new Gson().fromJson(new Gson().toJson(x),LikeDislike.class);
                            dislike.add(ld1);
                        });
                    }
                    lds.addAll(like);
                    lds.addAll(dislike);
                    if(lds.size()>0){
                    initFilesList();
                    setadaptor();
                    populateAdaptor();}
                    else{   textView.setVisibility(View.VISIBLE);
                            ldtitle.setVisibility(View.INVISIBLE);
                    }
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
        //set empty page msg
        TextView emptyView = findViewById(R.id.hist_blankMsg);
        listView.setEmptyView(emptyView);
        if (listView != null) {
            adapter = new LikeDislikeAdapter(this,like,dislike,this);
            adapter.setLds(dynlds);
            listView.setAdapter(adapter);
        }

    }
    private void initFilesList(){
        for(int i=0; i<lds.size(); i++){
            String filename = "image";
            filename += String.format("%s", i);
            File f = initFile(filename);
            listOfFiles.add(f);
        }
    }

    private File initFile(String filename){
        File f = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(f, filename);
    }
    private void populateAdaptor(){
        for(int i=0; i<lds.size(); i++){
            startService(lds.get(i), listOfFiles.get(i));
        }
    }
    private void startService(LikeDislike bm, File file){
        Intent intent = new Intent(getApplicationContext(), MyNewsService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(LIKE_DISLIKE, bm);
        bundle.putSerializable("LDFILE", file);
        intent.putExtra(LIKE_DISLIKE, bundle);
        startService(intent);
    }
}