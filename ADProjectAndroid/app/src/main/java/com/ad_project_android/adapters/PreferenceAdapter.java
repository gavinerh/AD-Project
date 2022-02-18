package com.ad_project_android.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ad_project_android.Preference;
import com.ad_project_android.R;

import java.util.ArrayList;
import java.util.List;

public class PreferenceAdapter extends ArrayAdapter<Object> {
    private Context context;
    Preference preference;
    private List<String> prefs = new ArrayList<>();
    private CheckBox pref;

    public PreferenceAdapter(@NonNull Context context,@NonNull List<String> prefs) {
        super(context, R.layout.row_pref);
        this.prefs = prefs;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent) {
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.row_pref, parent, false);
        }
        pref = v.findViewById(R.id.preferences);
        pref.setText(prefs.get(position));

        return v;
    }

    @Override
    public int getCount() {
        return prefs.size();
    }
}
