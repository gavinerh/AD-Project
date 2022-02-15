package com.ad_project_android.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ad_project_android.BookmarkPage;
import com.ad_project_android.R;
import com.ad_project_android.model.Bookmark;
import com.ad_project_android.model.NewsObject;
//import com.ad_project_android.model.Bookmark;

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
    private Boolean[] bookmarkonoff;
    private ToggleButton bookmarkBtn;

    public BookmarkAdapter(@NonNull Context context, @NonNull List<Bookmark> bookmarks,
                           BookmarkPage bkmarked) {
        super(context, R.layout.row);
        this.context = context;
        this.bkmarked = bkmarked;
        this.bookmarks = bookmarks;
        bms.addAll(bookmarks);
        getTogglelength();
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
        textView.setText(bms.get(position).getTitle());
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

        bookmarkBtn = convertView.findViewById(R.id.bookmark);
        if (bookmarks.contains(new Bookmark(bms.get(position).getTitle()))) {
            bookmarkBtn.setVisibility(View.VISIBLE);
            bookmarkBtn.setChecked(true);
            bookmarkonoff[rowpos] = true;
        }
        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bkmarked.setbmDialog(bms.get(position), 2);
            }
        });
        bookmarkBtn.setChecked(bookmarkonoff[position]);
        return convertView;
    }

    @Override
    public int getCount() {
        return bms.size();
    }

    public void getTogglelength() {
        bookmarkonoff = new Boolean[bms.size()];
        for (int i = 0; i < bms.size(); i++) {
            bookmarkonoff[i] = false;
        }
    }

    public void setBookmarks(List<Bookmark> bookmarks) { this.bookmarks = bookmarks; }
    public List<Bookmark> getBookmarks() { return bookmarks; }

    public void setBms(List<Bookmark> bms) {
        this.bms = bms;
    }
    public List<Bookmark> getBms() {
        return bms;
    }

    public ToggleButton getBookmarkBtn() {
        return bookmarkBtn;
    }
    public void setBookmarkBtn(ToggleButton bookmarkBtn) {
        this.bookmarkBtn = bookmarkBtn;
    }

}
