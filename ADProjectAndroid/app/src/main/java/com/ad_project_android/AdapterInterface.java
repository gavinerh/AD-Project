package com.ad_project_android;

import com.ad_project_android.model.NewsObject;

public interface AdapterInterface {
    void sendNewsObjectPosition(int position, int preference);
    void shareNews(String url);
    void launchWebview(String url);
}
