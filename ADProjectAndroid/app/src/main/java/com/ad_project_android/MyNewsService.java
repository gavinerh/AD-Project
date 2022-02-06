package com.ad_project_android;

import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ad_project_android.DataService.NewsService;
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

public class MyNewsService extends IntentService {
    private NewsObject newsObject = null;
    private File file = null;
    private int result = Activity.RESULT_CANCELED;
    public static final String NOTIFICATION = "com.ad_project_android.MyNewsService";
    public String tag = "Download service";

    public MyNewsService() {
        super("MyNewsService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // runs asynchronously
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // download images to file
        assert intent != null;
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        if(bundle != null){
            newsObject = (NewsObject) bundle.getSerializable("NEWSOBJECT");
            file = (File) bundle.getSerializable("FILE");
            downloadImages();
            notifyDownloadComplete();
        }
    }

    private void downloadImages(){
        // download images from received image url
        ImageDownloader.downloadImage(newsObject.getImageUrl(), file);
    }

    private void notifyDownloadComplete(){
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra("FILE", file);
        intent.putExtra("NEWSOBJECT", newsObject);
        sendBroadcast(intent);
    }
}