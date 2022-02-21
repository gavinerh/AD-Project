package com.ad_project_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.ad_project_android.DataService.NewsService;
import com.ad_project_android.adapters.LikeDislikeAdapter;
import com.ad_project_android.adapters.PreferenceAdapter;
import com.ad_project_android.model.CategoryJson;
import com.ad_project_android.model.LikeDislike;
import com.ad_project_android.services.Logout;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Preference extends AppCompatActivity {
    String tokenString;
    PreferenceAdapter adapter;
    List<String> categories = new ArrayList<>();
    List<String> cats = new ArrayList<>();
    List<CategoryJson> usercats = new ArrayList<>();
    CoordinatorLayout mCoordinate;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        populateTokenString();
        mCoordinate = findViewById(R.id.coordinator);
        if (tokenString == null) {
            getPreference();
        } else if (tokenString != null) {
            getUserPreference();
        }

    }

    public void onCheckboxClicked(String pref) {
        if (!categories.contains(pref)) {
            categories.add(pref);
            snackbar = Snackbar.make(mCoordinate, "Preference added", Snackbar.LENGTH_SHORT);
            snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
            snackbar.setAction("Close", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        } else {
            categories.remove(pref);
            snackbar = Snackbar.make(mCoordinate, "Preference removed", Snackbar.LENGTH_SHORT);
            snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
            snackbar.setAction("X", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        }

    }

    public void onCheckboxClicked(CategoryJson pref) {
        usercats.stream().forEach(x -> {
            if (x.getName() == pref.getName()) {
                x.setSelect(pref.isSelect());
                snackbar = Snackbar.make(mCoordinate, "Your Preference changed", Snackbar.LENGTH_SHORT);
                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                snackbar.setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }
        });
        adapter.setUserpref(usercats);
        adapter.notifyDataSetChanged();
    }

    public void onSubmit(View view) {
        if (tokenString == null) {
            if (categories.size() < 2) {
                Snackbar.make(view, "Please choose minimun 2 Categories", Snackbar.LENGTH_SHORT).show();
                return;
            } else {
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra("categories", (Serializable) categories);
                startActivity(intent);
            }
        } else if (tokenString != null) {
            List<CategoryJson> t = usercats.stream().filter(x -> x.isSelect())
                    .collect(Collectors.toList());
            if (t.size() < 2) {
                Snackbar.make(view, "Please choose minimun 2 Categories", Snackbar.LENGTH_SHORT).show();
                return;
            } else {
                postUserPreference();
            }
        }


    }

    private void populateTokenString() {
        SharedPreferences pref = getSharedPreferences(LoginActivity.USER_CREDENTIAL, MODE_PRIVATE);
        tokenString = pref.getString("token", null);
    }

    private NewsService getNewsServiceInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(NewsService.class);
    }

    private void getPreference() {
        NewsService newsService = getNewsServiceInstance();

        Call<List<String>> call = newsService.getCats();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.code() == 200) {
                    cats = response.body();
                    setadaptor();
                }
                else {
                    Logout.logout(Preference.this);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(Preference.this, "Server error, Try again later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUserPreference() {
        NewsService newsService = getNewsServiceInstance();

        Call<List<CategoryJson>> call = newsService.getUserCats(tokenString);
        call.enqueue(new Callback<List<CategoryJson>>() {
            @Override
            public void onResponse(Call<List<CategoryJson>> call, Response<List<CategoryJson>> response) {
                if (response.code() == 200) {
                    usercats = response.body();
                    setadaptor1();
                }
                else {
                    Logout.logout(Preference.this);
                }
            }

            @Override
            public void onFailure(Call<List<CategoryJson>> call, Throwable t) {
                Toast.makeText(Preference.this, "Server error, Try again later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postUserPreference() {
        NewsService newsService = getNewsServiceInstance();

        Call<Void> call = newsService.postUserCats(usercats, tokenString);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Toast.makeText(getApplicationContext(), "Preferences changed Successfully",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Logout.logout(Preference.this);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Preference.this, "Server error, Try again later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setadaptor() {
        ListView listView = findViewById(R.id.listView);
        if (listView != null) {
            adapter = new PreferenceAdapter(this, cats, null, this);
            listView.setAdapter(adapter);
        }

    }

    private void setadaptor1() {
        ListView listView = findViewById(R.id.listView);
        if (listView != null) {
            adapter = new PreferenceAdapter(this, null, usercats, this);
            listView.setAdapter(adapter);
        }
    }
}