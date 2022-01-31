package com.ad_project_android.services;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageDownloader {
    public static boolean downloadImage(String imgurl, File destFile){
        try{
            URL url = new URL(imgurl);
            URLConnection conn = url.openConnection();

            InputStream in = conn.getInputStream();
            FileOutputStream out = new FileOutputStream(destFile);

            byte[] buf = new byte[1024];
            int bytesRead = -1;
            while ((bytesRead = in.read(buf)) !=-1){
                out.write(buf, 0, bytesRead);
            }
            out.close();
            in.close();
            return true;
        } catch (Exception e){
            Log.d("Error downloading images", "Downloading error");
            return false;
        }
    }
}
