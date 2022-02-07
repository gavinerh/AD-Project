package com.ad_project_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebViewActivity extends AppCompatActivity {
    private String mUrl;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        mUrl = intent.getStringExtra(MainActivity.EXTERNAL_URL);

        mWebView = findViewById(R.id.web_view);
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setMax(100);
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(
                    WebView webView, int newprogress){
                if(newprogress == 100)
                    mProgressBar.setVisibility(View.GONE);
                else
                    mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(newprogress);
            }

        });
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(mUrl);
    }
}