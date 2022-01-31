package com.ad_project_android.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ad_project_android.R;
import com.ad_project_android.model.SettingModel;

import java.util.List;

public class ViewSettingAdapter extends ArrayAdapter<Object> {
    private final Context context;
    protected List<SettingModel> settingList;

    public ViewSettingAdapter(Context context, List<SettingModel> settingList){
        super(context, R.layout.settings_item);
        this.context = context;
        this.settingList = settingList;

        addAll(new Object[settingList.size()]);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.settings_item, parent, false);
        }
        ImageView imageView = convertView.findViewById(R.id.settingIcon);
        int id = context.getResources().getIdentifier(settingList.get(position).getIconFilename(), "drawable", context.getPackageName());
        imageView.setImageResource(id);

        TextView textView = convertView.findViewById(R.id.settingText);
        textView.setText(settingList.get(position).getName());
        return convertView;
    }
}
