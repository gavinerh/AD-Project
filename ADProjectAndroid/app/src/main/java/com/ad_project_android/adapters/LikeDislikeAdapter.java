package com.ad_project_android.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ad_project_android.History;
import com.ad_project_android.R;
import com.ad_project_android.model.LikeDislike;

import java.util.ArrayList;
import java.util.List;

public class LikeDislikeAdapter extends ArrayAdapter<Object> {
    private Context context;
    private History history;
    private List<LikeDislike> likes = new ArrayList<>();
    private List<LikeDislike> dislikes = new ArrayList<>();
    private List<LikeDislike> lds = new ArrayList<>();
    private ToggleButton likeBtn;
    private ToggleButton dislikeBtn;

    public LikeDislikeAdapter(@NonNull Context context, @NonNull List<LikeDislike> likes, List<LikeDislike> dislike, History history) {
        super(context, R.layout.row);
        this.context = context;
        this.history = history;
        this.likes = likes;
        this.dislikes = dislike;
        lds.addAll(likes);
        lds.addAll(dislike);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row, parent, false);
        }
        if(lds.get(position)!=null) {
            TextView textView = convertView.findViewById(R.id.headlineText);
            textView.setText(lds.get(position).getTitle());
            ImageView imageView = convertView.findViewById(R.id.imageView);
            imageView.setImageBitmap(lds.get(position).getBitmap());
            ImageView mShare = convertView.findViewById(R.id.share);
            mShare.setVisibility(View.GONE);
            TextView mTime = convertView.findViewById(R.id.dateText);
            mTime.setVisibility(View.GONE);
            TextView mSource = convertView.findViewById(R.id.sourceText);
            mSource.setVisibility(View.GONE);
            likeBtn = convertView.findViewById(R.id.like);
            dislikeBtn = convertView.findViewById(R.id.dislike);
            ToggleButton bm = convertView.findViewById(R.id.bookmark);
            bm.setVisibility(View.GONE);
        }
        if (likes.contains(lds.get(position))) {
            likeBtn.setVisibility(View.VISIBLE);
            likeBtn.setChecked(true);
            dislikeBtn.setVisibility(View.GONE);
        }
        if (dislikes.contains(lds.get(position))) {
            dislikeBtn.setVisibility(View.VISIBLE);
            dislikeBtn.setChecked(true);
            likeBtn.setVisibility(View.GONE);
        }
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                history.setDialog(lds.get(position), 1);

            }
        });
        dislikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                history.setDialog(lds.get(position), -1);
            }
        });
       
        return convertView;
    }

    @Override
    public int getCount() {
        return lds.size();
    }

    public void setLds(List<LikeDislike> lds) {
        this.lds = lds;
    }

    public List<LikeDislike> getLds() {
        return lds;
    }

}
