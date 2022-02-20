package com.ad_project_android.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ad_project_android.Preference;
import com.ad_project_android.R;
import com.ad_project_android.model.CategoryJson;

import java.util.ArrayList;
import java.util.List;

public class PreferenceAdapter extends ArrayAdapter<Object> {
    private Context context;
    Preference preference;
    private List<String> prefs = new ArrayList<>();
    private List<CategoryJson> userpref = new ArrayList<>();
    private CheckBox pref;

    public PreferenceAdapter(@NonNull Context context,@NonNull List<String> prefs,@NonNull List<CategoryJson> userprefs,
                             Preference preference) {
        super(context, R.layout.row_pref);
        this.prefs = prefs;
        this.context = context;
        this.userpref = userprefs;
        this.preference = preference;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent) {
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.row_pref, parent, false);
        }
        pref = v.findViewById(R.id.preferences);
        if(prefs!=null){
        pref.setText(prefs.get(position));
        pref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preference.onCheckboxClicked(prefs.get(position));
            }
        });
        }
        if(userpref!=null){
            pref.setText(userpref.get(position).getName());
            pref.setChecked(userpref.get(position).isSelect());
            pref.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CategoryJson cj = userpref.get(position);
                    if(cj.isSelect()){
                        cj.setSelect(false);
//                        pref.toggle();
                    }
                    else if(!cj.isSelect()){
                        cj.setSelect(true);
//                        pref.toggle();
                    }
                    preference.onCheckboxClicked(cj);
                }
            });

        }
        return v;
    }

    @Override
    public int getCount() {
        if(prefs!=null && prefs.size()>0){
        return prefs.size();}
        else{
            return userpref.size();
        }
    }

    public void setUserpref(List<CategoryJson> userpref) {
        this.userpref = userpref;
    }
}
