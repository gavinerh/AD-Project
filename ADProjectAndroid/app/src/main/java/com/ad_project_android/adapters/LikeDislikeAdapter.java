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
import com.ad_project_android.model.NewsObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LikeDislikeAdapter extends ArrayAdapter<Object> {
    private Context context;
    private History history;
    private List<String> likes = new ArrayList<>();
    private List<String> dislikes = new ArrayList<>();
    private List<String> lds = new ArrayList<>();
    private Boolean[] likeonoff;
    private Boolean[] dislikeonoff;
    private ToggleButton likeBtn;
    private ToggleButton dislikeBtn;

    public LikeDislikeAdapter(@NonNull Context context, @NonNull List<String> likes, List<String> dislike, History history) {
        super(context, R.layout.row);
        this.context = context;
        this.history = history;
        this.likes = likes;
        this.dislikes = dislike;
        lds.addAll(likes);
        lds.addAll(dislike);
        getTogglelength();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final int rowpos = position;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.headlineText);
        textView.setText(lds.get(position));
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
        if (likes.contains(lds.get(position))) {
            likeBtn.setVisibility(View.VISIBLE);
            likeBtn.setChecked(true);
            likeonoff[rowpos] = true;
            dislikeBtn.setVisibility(View.GONE);
        }
        if (dislikes.contains(lds.get(position))) {
            dislikeBtn.setVisibility(View.VISIBLE);
            dislikeBtn.setChecked(true);
            dislikeonoff[rowpos] = true;
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
        likeBtn.setChecked(likeonoff[position]);
        dislikeBtn.setChecked(dislikeonoff[position]);
        return convertView;
    }

    @Override
    public int getCount() {
        return lds.size();
    }

    public void getTogglelength() {
        likeonoff = new Boolean[lds.size()];
        dislikeonoff = new Boolean[lds.size()];
        for (int i = 0; i < lds.size(); i++) {
            likeonoff[i] = false;
            dislikeonoff[i] = false;
        }
    }

    public void setLds(List<String> lds) {
        this.lds = lds;
    }

    public List<String> getLds() {
        return lds;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public List<String> getDislikes() {
        return dislikes;
    }

    public void setDislikes(List<String> dislikes) {
        this.dislikes = dislikes;
    }

    public ToggleButton getLikeBtn() {
        return likeBtn;
    }

    public void setLikeBtn(ToggleButton likeBtn) {
        this.likeBtn = likeBtn;
    }

    public ToggleButton getDislikeBtn() {
        return dislikeBtn;
    }

    public void setDislikeBtn(ToggleButton dislikeBtn) {
        this.dislikeBtn = dislikeBtn;
    }
}
