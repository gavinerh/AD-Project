package com.ad_project_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ad_project_android.DataService.NewsService;
import com.ad_project_android.DataService.UserService;
import com.ad_project_android.adapters.MyAdapter;
import com.ad_project_android.adapters.NewsAdapter;
import com.ad_project_android.adapters.ViewSettingAdapter;
import com.ad_project_android.model.NewsObject;
import com.ad_project_android.model.User;
import com.ad_project_android.services.ImageDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button logoutBtn;
    ImageView userIcon;
    public static List<NewsObject> newsObjects = new ArrayList<NewsObject>();
    private boolean isNewsDataFetched = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrieveInfoFromServer();

        Log.d("News onCreate",""+newsObjects.size());

        //logoutBtn = findViewById(R.id.logoutBtn);
        //logoutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                logout();
//            }
//        });

//        userIcon = findViewById(R.id.userIcon);
        //createMockNewsObject(); // replace with getting json list from server must let function return true
        // after getting image url from server, go and download the images
        if(isNewsDataFetched){ // use this function inside the onSuccess retrofit call

            //downloadImages();
        }
//        NewsAdapter adapter = new NewsAdapter(this, newsObjects);
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

    private void createMockNewsObject(){
        String imageUrl = "https://sg.images.search.yahoo.com/images/view;_ylt=AwrwJU2ZQPdha3IAQ0Ml4gt.;_ylu=c2VjA3NyBHNsawNpbWcEb2lkA2FjZGNhZjgxMGNiYTVjNTA4YTc5ODVhY2RiZjFkYTgyBGdwb3MDNgRpdANiaW5n?back=https%3A%2F%2Fsg.images.search.yahoo.com%2Fsearch%2Fimages%3Fp%3Drandom%2Bimagge%26type%3DE210SG714G0%26fr%3Dmcafee%26fr2%3Dpiv-web%26tab%3Dorganic%26ri%3D6&w=564&h=634&imgurl=images6.fanpop.com%2Fimage%2Fphotos%2F42800000%2Frandom-random-42843735-564-634.jpg&rurl=http%3A%2F%2Fwww.fanpop.com%2Fclubs%2Frandom%2Fimages%2F42843735%2Ftitle%2Frandom-photo&size=52.3KB&p=random+imagge&oid=acdcaf810cba5c508a7985acdbf1da82&fr2=piv-web&fr=mcafee&tt=random+-+Random+Photo+%2842843735%29+-+Fanpop&b=0&ni=21&no=6&ts=&tab=organic&sigr=xM7r3jthWpxd&sigb=nnCOiXL7hvUQ&sigi=jxv8cQ_Vc7MC&sigt=1.zBgnZIXzvm&.crumb=b0UsnYb3SNe&fr=mcafee&fr2=piv-web&type=E210SG714G0";
        newsObjects.add(new NewsObject("Random news title", imageUrl, imageUrl, "Random description for news title"));
        newsObjects.add(new NewsObject("Random news title", imageUrl, imageUrl, "Random description for news title"));
        newsObjects.add(new NewsObject("Random news title", imageUrl, imageUrl, "Random description for news title"));
        newsObjects.add(new NewsObject("Random news title", imageUrl, imageUrl, "Random description for news title"));
        newsObjects.add(new NewsObject("Random news title", imageUrl, imageUrl, "Random description for news title"));
        newsObjects.add(new NewsObject("Random news title", imageUrl, imageUrl, "Random description for news title"));
        newsObjects.add(new NewsObject("Random news title", imageUrl, imageUrl, "Random description for news title"));
        newsObjects.add(new NewsObject("Random news title", imageUrl, imageUrl, "Random description for news title"));
        newsObjects.add(new NewsObject("Random news title", imageUrl, imageUrl, "Random description for news title"));
        isNewsDataFetched = true;
    }

    private void downloadImages(){
        // download images from received image url
        for(int i=0; i<newsObjects.size(); i++){
            String filename = "image";
            filename += String.format("%s", i);
            File f = initFile(filename);
            ImageDownloader.downloadImage(newsObjects.get(i).getImageUrl(), f);
        }
    }

    private File initFile(String filename){
        File f = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(f, filename);
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
                        newsObjects = response.body();
                        Log.d("News onResponse",""+newsObjects.size());
                        setadaptor(newsObjects);
                    }
                }
                @Override
                public void onFailure(Call<List<NewsObject>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Apologies, Server is currently experiencing some unknown error, try again later", Toast.LENGTH_SHORT).show();

                }
            });
    }
    private void setadaptor(List<NewsObject> newsObjects){
        MyAdapter adapter;
        Toolbar mToolbar;
        ListView listView = findViewById(R.id.listView);
        if (listView != null) {
            adapter = new MyAdapter(this,newsObjects );
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

}