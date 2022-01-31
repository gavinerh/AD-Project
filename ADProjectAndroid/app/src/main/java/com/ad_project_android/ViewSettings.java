package com.ad_project_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ad_project_android.model.SettingModel;

import java.util.ArrayList;
import java.util.List;

public class ViewSettings extends AppCompatActivity {
    // set up a list view
    List<SettingModel> listOfSetting = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_settings);

        populateSettingList();
    }

    private void populateSettingList(){
        listOfSetting.add(new SettingModel("preference", "Set Preference"));
        listOfSetting.add(new SettingModel("setting", "Account Settings"));
        listOfSetting.add(new SettingModel("history", "View History"));
        listOfSetting.add(new SettingModel("feedback", "Feedback"));
    }
}