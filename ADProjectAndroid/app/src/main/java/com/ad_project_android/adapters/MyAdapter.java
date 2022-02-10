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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.ad_project_android.AdapterInterface;
import com.ad_project_android.R;
import com.ad_project_android.model.NewsObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyAdapter extends ArrayAdapter<Object> implements AdapterInterface {
    private final Context context;
    public static final String Likes = "Likes";
    public static final String Dislikes = "Dislikes";
    protected List<NewsObject> myitems = new ArrayList<>();
    private ArrayList<File> files = null;
    private AdapterInterface adapterInterface;
//    static Set<String> likes = new HashSet<>();
//    static Set<String> dislikes = new HashSet<>();
    Animation ani;

    public MyAdapter(Context context, List<NewsObject> myitems, ArrayList<File> files, AdapterInterface adapterInterface) {
        super(context, R.layout.row);
        this.adapterInterface = adapterInterface;
        this.context = context;
        this.myitems = myitems;
        this.files = files;
    }

    public View getView(int pos, View view, @NonNull ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Activity.LAYOUT_INFLATER_SERVICE);

            // if we are not responsible for adding the view to the parent,
            // then attachToRoot should be 'false' (which is in our case)
            view = inflater.inflate(R.layout.row, parent, false);
        }
        if(myitems.size() == 0){
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
        mTime.setText(myitems.get(pos).getPrettytime());
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


            ToggleButton likeBtn = mCardView.findViewById(R.id.like);
            ToggleButton dislikeBtn = mCardView.findViewById(R.id.dislike);
//            final SharedPreferences pref = context.getSharedPreferences("LikeDislike",Context.MODE_PRIVATE);
//            if(pref.contains(Likes)){
//                likes = pref.getStringSet(Likes,null);
//            }
//            if(pref.contains(Dislikes)){
//                dislikes = pref.getStringSet(Dislikes,null);
//            }
//            final SharedPreferences.Editor editor = pref.edit();
            if(likeBtn!=null){
                likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(likeBtn.isChecked()){
//                        likes.add(myitems.get(pos).getTitle());
//                        editor.putStringSet(Likes,likes);
//                        editor.commit();
                        sendNewsObjectPosition(myitems.get(pos), 1);
                        if(dislikeBtn.isChecked()){
                            dislikeBtn.setChecked(false);
//                            dislikes.remove(myitems.get(pos).getTitle());
//                            editor.putStringSet(Dislikes,dislikes);
//                            editor.commit();
                            sendNewsObjectPosition(myitems.get(pos), -1);
                        }
                    }
                    else if(!likeBtn.isChecked()){
//                        likes.remove(myitems.get(pos).getTitle());
//                        editor.putStringSet(Likes,likes);
//                        editor.commit();
                        sendNewsObjectPosition(myitems.get(pos), 1);
                    }
                }
            });
        }
        if(dislikeBtn!=null){
            dislikeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dislikeBtn.isChecked()){
//                        dislikes.add(myitems.get(pos).getTitle());
//                        editor.putStringSet(Dislikes,dislikes);
//                        editor.commit();
                        sendNewsObjectPosition(myitems.get(pos), -1);
                        if(likeBtn.isChecked()){
                            likeBtn.setChecked(false);
//                            likes.remove(myitems.get(pos).getTitle());
//                            editor.putStringSet(Likes,likes);
//                            editor.commit();
                            sendNewsObjectPosition(myitems.get(pos), 1);
                        }
                    }
                    else if(!dislikeBtn.isChecked()){
//                        dislikes.remove(myitems.get(pos).getTitle());
//                        editor.putStringSet(Dislikes,dislikes);
//                        editor.commit();
                        sendNewsObjectPosition(myitems.get(pos), -1);
                    }
                }
            });
        }

        return view;
    }
    public void filterlist(List<NewsObject> filterlist, ArrayList<File> filterfile){
        myitems = filterlist;
        files = filterfile;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return myitems.size();
    }

    private void setImageBitmap(File f, ImageView imgView, NewsObject newsObject){
        Bitmap bitmap = null;
        if(newsObject.getBitmap() != null){
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
}
