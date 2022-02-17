package com.ad_project_android;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ad_project_android.adapters.ViewSettingAdapter;
import com.ad_project_android.model.SettingModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewSettings extends AppCompatActivity implements AdapterView.OnItemClickListener {
    // set up a list view
    List<SettingModel> listOfSetting = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_settings);

        populateSettingList();
        ListView listView = findViewById(R.id.settingListView);
        ViewSettingAdapter settingAdapter = new ViewSettingAdapter(this, listOfSetting);
        if(listView != null){
            listView.setAdapter(settingAdapter);
            listView.setOnItemClickListener(this);
        }
    }

    private void populateSettingList(){
        listOfSetting = new ArrayList<>();
        listOfSetting.add(new SettingModel("ic_baseline_bookmark_added_grey_24", "Bookmarks"));
        listOfSetting.add(new SettingModel("ic_baseline_history_24", "Like/Dislike History"));
        listOfSetting.add(new SettingModel("ic_baseline_checklist_24", "News Preference"));
        listOfSetting.add(new SettingModel("ic_baseline_settings_24", "Account"));
        listOfSetting.add(new SettingModel("ic_baseline_logout_24", "Logout"));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView textView = view.findViewById(R.id.settingText);
        if(textView.getText().toString()=="Logout"){
           logout();
        }
        else{
        Intent intent = getLinkedIntent(textView.getText().toString());
        startActivity(intent);

        }
    }

    private Intent getLinkedIntent(String text){
        switch (text) {
            case "News Preference":
                return new Intent(this, Preference.class);
            case "Account":
                return new Intent(this, AccountActivity.class);
            case "Like/Dislike History":
                return new Intent(this, History.class);
            case "Bookmarks":
                return new Intent(this, BookmarkPage.class);
            default:
                return null;
        }
    }
    private void logout(){
        removeStoredPreference();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("finish", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
        startActivity(intent);
        finish();
    }
    private void removeStoredPreference(){
        SharedPreferences pref = getSharedPreferences("user_credential", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}