package com.ad_project_android.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
//<<<<<<< Updated upstream
import android.graphics.Bitmap;
//=======
//>>>>>>> Stashed changes
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.ad_project_android.BookmarkPage;
import com.ad_project_android.R;
import com.ad_project_android.model.Bookmark;

import com.ad_project_android.model.NewsObject;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;

public class BookmarkAdapter extends ArrayAdapter<Object> {
    private Context context;
    private BookmarkPage bkmarked;
    private List<Bookmark> bookmarks = new ArrayList<>();
    private List<Bookmark> bms = new ArrayList<>();
    private ToggleButton bookmarkBtn;
    Animation ani;

    public BookmarkAdapter(@NonNull Context context, @NonNull List<Bookmark> bookmarks,
                           BookmarkPage bkmarked) {
        super(context, R.layout.row);
        this.context = context;
        this.bkmarked = bkmarked;
        this.bookmarks = bookmarks;
        bms.addAll(bookmarks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final int rowpos = position;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row,
                    parent, false);
        }

        TextView textView = convertView.findViewById(R.id.headlineText);
        //truncate
        String title = bms.get(position).getTitle();
        if(title.length()<80) {textView.setText(title);}
        else {
            title=title.substring(0,80);
            textView.setText(title+"...");
        }

        ImageView img = convertView.findViewById(R.id.imageView);
        img.setImageBitmap(bms.get(position).getBitmap());

        ImageView mShare = convertView.findViewById(R.id.share);
        mShare.setVisibility(View.GONE);
        TextView mTime = convertView.findViewById(R.id.dateText);
        mTime.setVisibility(View.GONE);
        TextView mSource = convertView.findViewById(R.id.sourceText);
        mSource.setVisibility(View.GONE);

        ToggleButton likeBtn = convertView.findViewById(R.id.like);
        likeBtn.setVisibility(View.GONE);
        ToggleButton dislikeBtn = convertView.findViewById(R.id.dislike);
        dislikeBtn.setVisibility(View.GONE);

        CardView mCardView = convertView.findViewById(R.id.card_view);
        mCardView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                //go to webview? go to external url
                String externalUrl = bms.get(position).getUrl();
                ani = AnimationUtils.loadAnimation(context, R.drawable.overshoot);
                ani.start();
                mCardView.startAnimation(ani);
                bkmarked.launchwebview(externalUrl);
            }
        });

        bookmarkBtn = convertView.findViewById(R.id.bookmark);
        if (bookmarks.contains(bms.get(position))) {
            bookmarkBtn.setVisibility(View.VISIBLE);
            bookmarkBtn.setChecked(true);
        }
        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bkmarked.setbmDialog(bms.get(position), 2);
            }
        });
        return convertView;
    }

    @Override
    public int getCount() {
        return bms.size();
    }

    public void setBms(List<Bookmark> bms) {
        this.bms = bms;
    }
    public List<Bookmark> getBms() {
        return bms;
    }

}