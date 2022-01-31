package com.ad_project_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ad_project_android.adapters.NewsAdapter;
import com.ad_project_android.model.NewsObject;
import com.ad_project_android.services.ImageDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageView userIcon;
    List<NewsObject> newsObjects = new ArrayList<NewsObject>();
    private boolean isNewsDataFetched;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        userIcon = findViewById(R.id.userIcon);
        createMockNewsObject(); // replace with getting json list from server must let function return true
        // after getting image url from server, go and download the images
        if(isNewsDataFetched){ // use this function inside the onSuccess retrofit call
            downloadImages();
        }
//        NewsAdapter adapter = new NewsAdapter(this, newsObjects);
    }

    public void viewSetting(View view){
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
}