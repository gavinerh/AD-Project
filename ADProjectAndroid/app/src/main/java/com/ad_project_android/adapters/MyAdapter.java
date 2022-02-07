package com.ad_project_android.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.ad_project_android.AdapterInterface;
import com.ad_project_android.R;
import com.ad_project_android.model.NewsObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends ArrayAdapter<Object> implements AdapterInterface {
    private final Context context;
    private Boolean like;
    protected List<NewsObject> myitems = new ArrayList<>();
    private ArrayList<File> files = null;
    private AdapterInterface adapterInterface;

    public MyAdapter(Context context, List<NewsObject> myitems, ArrayList<File> files, AdapterInterface adapterInterface) {
        super(context, R.layout.row);
        this.adapterInterface = adapterInterface;
        this.context = context;
        this.myitems = myitems;
        this.files = files;
        addAll(new Object[myitems.size()]);
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
            @Override
            public void onClick(View view) {
                //go to webview? go to external url
                String externalUrl = myitems.get(pos).getNewsUrl();
                Toast.makeText(context, externalUrl, Toast.LENGTH_SHORT).show();
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
        mTime.setText(myitems.get(pos).getPublishedAt());
        // set share to social media
        ImageView mShare = view.findViewById(R.id.share);
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //share to social media
                Toast.makeText(context, "Share to social media", Toast.LENGTH_SHORT).show();
                shareNews(myitems.get(pos).getNewsUrl());
            }
        });


        ImageView likeBtn = view.findViewById(R.id.like);
        ImageView dislikeBtn = view.findViewById(R.id.dislike);
        if(likeBtn!=null){
            likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(like==null|| !like){
                    likeBtn.setImageResource(R.drawable.ic_hand_thumbs_up_fill);
                    dislikeBtn.setImageResource(R.drawable.ic_hand_thumbs_down);
                    like = true;
                    sendNewsObjectPosition(pos, 1);
                    }
                    else{
                        likeBtn.setImageResource(R.drawable.ic_hand_thumbs_up);
                        like = null;
                    }
                }
            });
        }
        if(dislikeBtn!=null){
            dislikeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(like==null || like){
                        dislikeBtn.setImageResource(R.drawable.ic_hand_thumbs_down_fill);
                        likeBtn.setImageResource(R.drawable.ic_hand_thumbs_up);
                        like = false;
                        sendNewsObjectPosition(pos, -1);
                    }
                    else{
                        dislikeBtn.setImageResource(R.drawable.ic_hand_thumbs_down);
                        like = null;
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
    public void sendNewsObjectPosition(int position, int preference) {
        adapterInterface.sendNewsObjectPosition(position, preference);
    }

    @Override
    public void shareNews(String url) {
        adapterInterface.shareNews(url);
    }
}
