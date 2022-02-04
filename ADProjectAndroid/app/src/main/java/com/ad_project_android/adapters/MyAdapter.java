package com.ad_project_android.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ad_project_android.R;
import com.ad_project_android.model.NewsObject;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends ArrayAdapter<Object> {
    private final Context context;
    private Boolean like;
    protected List<NewsObject> myitems = new ArrayList<>();

    public MyAdapter(Context context, List<NewsObject> myitems) {
        super(context, R.layout.row);
        this.context = context;
        this.myitems = myitems;

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

        // set the image for ImageView
        ImageView imageView = view.findViewById(R.id.imageView);
//        int id = context.getResources().getIdentifier(myitems.get(pos).getToons(),
//            "drawable", context.getPackageName());
        imageView.setImageResource(R.drawable.user_icon);

        // set the text for TextView
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(myitems.get(pos).getTitle());

        TextView desc = view.findViewById(R.id.textView3);
        desc.setText(myitems.get(pos).getDescription());

        ImageView likeBtn = view.findViewById(R.id.like);
        ImageView dislikeBtn = view.findViewById(R.id.dislike);
        if(likeBtn!=null){
            likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(like==null|| !like){
                    likeBtn.setImageResource(R.drawable.like_filled);
                    dislikeBtn.setImageResource(R.drawable.dislike_nofill);
                    like = true;
                    }
                    else{
                        likeBtn.setImageResource(R.drawable.like_nofill);
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
                        dislikeBtn.setImageResource(R.drawable.dislike_filled);
                        likeBtn.setImageResource(R.drawable.like_nofill);
                        like = false;
                    }
                    else{
                        dislikeBtn.setImageResource(R.drawable.dislike_nofill);
                        like = null;
                    }
                }
            });
        }

        return view;
    }
    public void filterlist(List<NewsObject> filterlist){
        myitems = filterlist;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return myitems.size();
    }
}
