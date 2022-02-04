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
    private List<NewsObject> newsObjects = null;
    private ArrayList<File> listOfFiles = new ArrayList<>();
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
            newsObjects = (ArrayList<NewsObject>) bundle.getSerializable("NEWSOBJECT");
            downloadImages();
            notifyDownloadComplete();
        }
    }
    private File initFile(String filename){
        File f = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(f, filename);
    }
    private void downloadImages(){
        // download images from received image url
        for(int i=0; i<newsObjects.size(); i++){
            String filename = "image";
            filename += String.format("%s", i);
            File f = initFile(filename);
            listOfFiles.add(f);
            ImageDownloader.downloadImage(newsObjects.get(i).getImageUrl(), f);
        }
    }

    private void notifyDownloadComplete(){
        Intent intent = new Intent(NOTIFICATION);
        Bundle bundle = new Bundle();
        bundle.putSerializable("FILES", listOfFiles);
        intent.putExtra("BUNDLE", bundle);
        sendBroadcast(intent);
    }
}