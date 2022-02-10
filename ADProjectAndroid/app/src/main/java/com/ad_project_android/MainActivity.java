package com.ad_project_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.ad_project_android.DataService.NewsService;
import com.ad_project_android.adapters.MyAdapter;
import com.ad_project_android.model.NewsObject;
import com.ad_project_android.services.ImageDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterInterface {

    public static ArrayList<NewsObject> newsObjects = new ArrayList<NewsObject>();
    private ArrayList<File> listOfFiles = new ArrayList<>();
    private ArrayList<NewsObject> dynamicNewsObject = new ArrayList<>();
    private MyAdapter adapter = null;
    public static final String EXTERNAL_URL = "externalUrl";
    private static String tokenString = null;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            File file = (File) intent.getSerializableExtra("FILE");
            NewsObject newsObject = (NewsObject) intent.getSerializableExtra("NEWSOBJECT");
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            newsObject.setBitmap(bitmap);
            dynamicNewsObject.add(newsObject);
            adapter.notifyDataSetChanged();
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
        setContentView(R.layout.activity_main);
        populateTokenString();
        retrieveInfoFromServer();

        Log.d("News onCreate",""+newsObjects.size());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        MenuItem setting = menu.findItem(R.id.setting);
        setting.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                viewSetting();
                return false;
            }
        });
        //searchView.setQueryHint("Search here..");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        return true;
    }


    public void viewSetting(){
        Intent intent = new Intent(getApplicationContext(), ViewSettings.class);
        startActivity(intent);
    }

    private NewsService getNewsServiceInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(NewsService.class);
    }
    private void retrieveInfoFromServer(){
            // create instance of UserService api class
            NewsService newsService = getNewsServiceInstance();

            Call<List<NewsObject>> call = newsService.getNews(tokenString);

            call.enqueue(new Callback<List<NewsObject>>() {
                @Override
                public void onResponse(Call<List<NewsObject>> call, Response<List<NewsObject>> response) {
                    if(response.code() == 200){
                        // populate the form
                        newsObjects = (ArrayList<NewsObject>) response.body();
                        Log.d("News OnResponse",""+newsObjects.size());
                        initFilesList();
                        setadaptor(dynamicNewsObject);
                        populateAdaptor();
                    }else{
                        logout();
                    }
                }
                @Override
                public void onFailure(Call<List<NewsObject>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Server error, please try again later", Toast.LENGTH_SHORT).show();

                }
            });
    }
    private void setadaptor(List<NewsObject> newsObjects){
        Toolbar mToolbar;
        ListView listView = findViewById(R.id.listView);
        if (listView != null) {
            adapter = new MyAdapter(this,newsObjects, listOfFiles, this);
            listView.setAdapter(adapter);
        }
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        if(newsObjects.size()>0){
            Log.d("News Links",newsObjects.get(1).getTitle());}
        else {
            Log.d("News Links","0 items");
        }

    }
    private void filter(String text){
        List<NewsObject> filteritems = new ArrayList<>();
        ArrayList<File> filterFile = new ArrayList<>();
        for(int i=0; i<dynamicNewsObject.size(); i++){
            if(dynamicNewsObject.get(i).getTitle().toLowerCase().contains(text.toLowerCase())){
                filteritems.add(dynamicNewsObject.get(i));
                filterFile.add(listOfFiles.get(i));
            }
        }
        adapter.filterlist(filteritems,filterFile);
    }

    private void populateAdaptor(){
        for(int i=0; i<newsObjects.size(); i++){
            startService(newsObjects.get(i), listOfFiles.get(i));

        }
    }

    private void startService(NewsObject newsObject, File file){
        Intent intent = new Intent(getApplicationContext(), MyNewsService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("NEWSOBJECT", newsObject);
        bundle.putSerializable("FILE", file);
        intent.putExtra("BUNDLE", bundle);
        startService(intent);
    }

    @Override
    public void sendNewsObjectPosition(NewsObject position, int preference) {
        postLikeOrDislike(position, preference);
    }

    @Override
    public void shareNews(String url) {
        sharenews(url);
    }

    @Override
    public void launchWebview(String url) {
        launchwebview(url);
    }

    private void postLikeOrDislike(NewsObject newsObject, int preference){
        NewsService newsService = getNewsServiceInstance();
        Call<Void> call = null;
        if(preference == 1){
            call = newsService.postLike(newsObject, tokenString);
        } else{
            call = newsService.postDislike(newsObject, tokenString);
        }
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    Toast.makeText(MainActivity.this, "Preference is registered", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Server error, please update preference later", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initFilesList(){
        for(int i=0; i<newsObjects.size(); i++){
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
    private void sharenews(String url){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, ""+url);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent,null);
        startActivity(shareIntent);
    }
    private void launchwebview(String url){
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        intent.putExtra(EXTERNAL_URL, url);
        startActivity(intent);
    }

    private void populateTokenString(){
        SharedPreferences pref = getSharedPreferences(LoginActivity.USER_CREDENTIAL, MODE_PRIVATE);
        tokenString = pref.getString("token", null);
        if(tokenString == null){
            finish();
            return;
        }
    }

    private void logout(){
        SharedPreferences pref = getSharedPreferences(LoginActivity.USER_CREDENTIAL, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        finish();
    }
}