package com.ad_project_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterInterface {

    Button logoutBtn;
    ImageView userIcon;
    public static ArrayList<NewsObject> newsObjects = new ArrayList<NewsObject>();
    private boolean isNewsDataFetched = false;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getBundleExtra("BUNDLE");
            if(bundle != null){
                ArrayList<File> files = (ArrayList<File>) bundle.getSerializable("FILES");
                setadaptor(newsObjects, files);
            }
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

        retrieveInfoFromServer();

        Log.d("News onCreate",""+newsObjects.size());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        MenuItem logout = menu.findItem(R.id.logout);
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                logout();
                return false;
            }
        });
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
                //filter(newText);
                return false;
            }
        });
        return true;
    }


    public void viewSetting(){
        Intent intent = new Intent(getApplicationContext(), ViewSettings.class);
        startActivity(intent);
    }


    private void logout(){
        removeStoredPreference();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void removeStoredPreference(){
        SharedPreferences pref = getSharedPreferences("user_credential", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("email");
        editor.commit();
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

            Call<List<NewsObject>> call = newsService.getNews();

            call.enqueue(new Callback<List<NewsObject>>() {
                @Override
                public void onResponse(Call<List<NewsObject>> call, Response<List<NewsObject>> response) {
                    if(response.code() == 200){
                        // populate the form
                        newsObjects = (ArrayList<NewsObject>) response.body();
                        Log.d("News onResponse",""+newsObjects.size());
                        startService();
//                        setadaptor(newsObjects);
                    }
                }
                @Override
                public void onFailure(Call<List<NewsObject>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Apologies, Server is currently experiencing some unknown error, try again later", Toast.LENGTH_SHORT).show();

                }
            });
    }
    private void setadaptor(List<NewsObject> newsObjects, ArrayList<File> files){
        MyAdapter adapter;
        Toolbar mToolbar;
        ListView listView = findViewById(R.id.listView);
        if (listView != null) {
            adapter = new MyAdapter(this,newsObjects, files, this);
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

    private void startService(){
        Intent intent = new Intent(getApplicationContext(), MyNewsService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("NEWSOBJECT", newsObjects);
        intent.putExtra("BUNDLE", bundle);
        startService(intent);
    }

    @Override
    public void sendNewsObjectPosition(int position, int preference) {
        postLikeOrDislike(position, preference);
    }

    private void postLikeOrDislike(int position, int preference){
        NewsObject newsObject = newsObjects.get(position);
        NewsService newsService = getNewsServiceInstance();
        Call<Void> call = null;
        if(preference == 1){
            call = newsService.postLike(newsObject);
        } else{
            call = newsService.postDislike(newsObject);
        }
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    Toast.makeText(MainActivity.this, "Like preference is registered", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Server error, please update preference later", Toast.LENGTH_SHORT).show();
            }
        });

    }
}