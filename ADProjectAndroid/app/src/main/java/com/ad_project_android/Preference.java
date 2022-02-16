package com.ad_project_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Preference extends AppCompatActivity {
    Button submit;
    List<String> categories = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

    }
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.business:
                if (checked) categories.add("business");
                else
                    categories.remove("business");
                break;
            case R.id.entertainment:
                if(checked) categories.add("entertainment");
                else
                    categories.remove("entertainment");
                break;
            case R.id.health:
                if(checked) categories.add("health");
                else categories.remove("health");
                break;
            case R.id.science:
                if(checked) categories.add("science");
                else categories.remove("science");
                break;
            case R.id.sport:
                if(checked) categories.add("sports");
                else categories.remove("sports");
                break;
            case R.id.technology:
                if(checked) categories.add("technology");
                else categories.remove("technology");
                break;
            case R.id.general:
                if(checked) categories.add("general");
                else categories.remove("general");
                break;
        }
    }

    public void onSubmit(View view){
        if(categories.size()<2){
            Snackbar.make(view,"Please choose minimun 2 Categories",Snackbar.LENGTH_SHORT).show();
            return;
        }
        else{
            Intent intent = new Intent(this,RegisterActivity.class);
            intent.putExtra("categories", (Serializable) categories);
            startActivity(intent);
        }
    }

}