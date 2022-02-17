package com.ad_project_android.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.ad_project_android.AdapterInterface;
import com.ad_project_android.R;
import com.ad_project_android.model.Bookmark;
import com.ad_project_android.model.NewsObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyAdapter extends ArrayAdapter<Object> implements AdapterInterface {
    private final Context context;
    protected List<NewsObject> myitems = new ArrayList<>();
    private List<String> likes = new ArrayList<>();
    private List<String> dislikes = new ArrayList<>();
    private List<String> bms = new ArrayList<>();
    private ArrayList<File> files = null;
    private AdapterInterface adapterInterface;
    private Boolean[] likeonoff;
    private Boolean[] dislikeonoff;
    private Boolean[] bookmarkonoff;
    Animation ani;

    public MyAdapter(Context context, List<NewsObject> myitems, ArrayList<File> files, Boolean[] onff,
                     Boolean[] donff, Boolean[] bmonff, AdapterInterface adapterInterface) {
        super(context, R.layout.row);
        this.adapterInterface = adapterInterface;
        this.context = context;
        this.myitems = myitems;
        this.files = files;
        this.likeonoff = onff;
        this.dislikeonoff = donff;
        this.bookmarkonoff = bmonff;
    }

    public View getView(int pos, View view, @NonNull ViewGroup parent) {
        final int rowpos = pos;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Activity.LAYOUT_INFLATER_SERVICE);

            // if we are not responsible for adding the view to the parent,
            // then attachToRoot should be 'false' (which is in our case)
            view = inflater.inflate(R.layout.row, parent, false);
        }
        if (myitems.size() == 0) {
            return view;
        }

        CardView mCardView = view.findViewById(R.id.card_view);
        mCardView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                //go to webview? go to external url
                String externalUrl = myitems.get(pos).getNewsUrl();
                ani = AnimationUtils.loadAnimation(context, R.drawable.overshoot);
                ani.start();
                mCardView.startAnimation(ani);
                launchWebview(externalUrl);
            }
        });

        // set the image for ImageView
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.ic_baseline_image_24);
        setImageBitmap(files.get(pos), imageView, myitems.get(pos));
        // set headline
        TextView textView = view.findViewById(R.id.headlineText);
        textView.setText(myitems.get(pos).getTitle());
        //set news source
        TextView mSource = view.findViewById(R.id.sourceText);
        mSource.setText(myitems.get(pos).getSource().getName());
        // set time published
        TextView mTime = view.findViewById(R.id.dateText);
        mTime.setText("• " + myitems.get(pos).getPrettytime());
        // set share to social media
        ImageView mShare = view.findViewById(R.id.share);
        mShare.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                //share to social media
                Toast.makeText(context, "Share to social media", Toast.LENGTH_SHORT).show();
                //Animate the share button
                ani = AnimationUtils.loadAnimation(context, R.drawable.overshoot);
                ani.start();
                mShare.startAnimation(ani);
                //Share method from another activity
                shareNews(myitems.get(pos).getNewsUrl());
            }
        });


        ToggleButton likeBtn = view.findViewById(R.id.like);
        ToggleButton dislikeBtn = view.findViewById(R.id.dislike);
        ToggleButton bookmarkBtn = view.findViewById(R.id.bookmark);

        if (likes.contains(myitems.get(pos).getTitle())) {

            likeBtn.setChecked(true);
            likeonoff[rowpos] = true;
        }
        if (dislikes.contains(myitems.get(pos).getTitle())) {
            dislikeBtn.setChecked(true);
            dislikeonoff[rowpos] = true;
        }

        if (likeBtn != null) {
            likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (likeBtn.isChecked()) {
                        likeonoff[rowpos] = true; //save state
                        sendNewsObjectPosition(myitems.get(pos), 1);
                        if (dislikeBtn.isChecked()) {
                            dislikeBtn.setChecked(false);
                            dislikeonoff[rowpos] = false;
                            sendNewsObjectPosition(myitems.get(pos), -1);
                        }
                    } else if (!likeBtn.isChecked()) {
                        likeonoff[rowpos] = false;
                        sendNewsObjectPosition(myitems.get(pos), 1);
                    }
                }
            });
        }
        if (dislikeBtn != null) {
            dislikeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dislikeBtn.isChecked()) {
                        dislikeonoff[rowpos] = true;//save state
                        sendNewsObjectPosition(myitems.get(pos), -1);
                        if (likeBtn.isChecked()) {
                            likeBtn.setChecked(false);
                            likeonoff[rowpos] = false;
                            sendNewsObjectPosition(myitems.get(pos), 1);
                        }
                    } else if (!dislikeBtn.isChecked()) {
                        dislikeonoff[rowpos] = false;// save state
                        sendNewsObjectPosition(myitems.get(pos), -1);
                    }
                }
            });
        }

        likeBtn.setChecked(likeonoff[pos]);
        dislikeBtn.setChecked(dislikeonoff[pos]);

        //bookmark sharedpref and onclick
        if (bms.contains(myitems.get(pos).getTitle())) {
            bookmarkBtn.setChecked(true);
            bookmarkonoff[rowpos] = true;
        }

        if (bookmarkBtn != null) {
            bookmarkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bookmarkBtn.isChecked()) { //check if already bookmarked
                        bookmarkonoff[rowpos] = true;
                        sendNewsObjectPosition(myitems.get(pos), 2);
                    } else if (!bookmarkBtn.isChecked()) { //unbookmark
                        bookmarkonoff[rowpos] = false;
                        sendNewsObjectPosition(myitems.get(pos), 2);
                    }
                }
            });
        }
        bookmarkBtn.setChecked(bookmarkonoff[pos]);

        return view;
    }

    public void filterlist(List<NewsObject> filterlist, ArrayList<File> filterfile) {
        myitems = filterlist;
        files = filterfile;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return myitems.size();
    }

    private void setImageBitmap(File f, ImageView imgView, NewsObject newsObject) {
        Bitmap bitmap = null;
        if (newsObject.getBitmap() != null) {
            bitmap = newsObject.getBitmap();
        }
        imgView.setImageBitmap(bitmap);
    }

    @Override
    public void sendNewsObjectPosition(NewsObject position, int preference) {
        adapterInterface.sendNewsObjectPosition(position, preference);
    }

    @Override
    public void shareNews(String url) {
        adapterInterface.shareNews(url);
    }

    @Override
    public void launchWebview(String url) {
        adapterInterface.launchWebview(url);
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public void setDislikes(List<String> dislikes) {
        this.dislikes = dislikes;
    }

    public void setBms(List<String> bms) {
        this.bms = bms;
    }
}
