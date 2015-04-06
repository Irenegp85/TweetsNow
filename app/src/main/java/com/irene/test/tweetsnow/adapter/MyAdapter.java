package com.irene.test.tweetsnow.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.irene.test.tweetsnow.R;
import com.irene.test.tweetsnow.api.model.Tweet;
import com.irene.test.tweetsnow.model.TweetInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Class to display the information of the tweets
 *
 * Created by Irene on 01/04/2015.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<TweetInfo> mDataset;
    private Context ctxt;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tv_name;
        public TextView tv_text;
        public TextView tv_date;
        public TextView tv_retweet;
        public ImageView iv_user;
        public ViewHolder(View v) {
            super(v);
            tv_name = (TextView) v.findViewById(R.id.tv_name);
            tv_text = (TextView) v.findViewById(R.id.tv_text);
            tv_date = (TextView) v.findViewById(R.id.tv_date);
            tv_retweet = (TextView) v.findViewById(R.id.tv_retweet);
            iv_user = (ImageView) v.findViewById(R.id.iv_user);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<TweetInfo> myDataset, Context c) {
        mDataset = myDataset;
        ctxt = c;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweet_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tv_name.setText(Html.fromHtml("<b>"+mDataset.get(position).getName()+ "</b> @" +mDataset.get(position).getScreenName()));
        holder.tv_text.setText(Html.fromHtml(mDataset.get(position).getText()));

        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
        format.setLenient(true);
        SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss - dd MMM yyyy");
        String strDatePublish = "";
        try {
            Date newDate = format.parse(mDataset.get(position).getDateCreated());
            strDatePublish = format2.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tv_date.setText(strDatePublish);
        holder.tv_retweet.setText(String.valueOf(mDataset.get(position).getRetWeetCount()));

        Glide.with(ctxt)
                .load(mDataset.get(position).getImage())
                .centerCrop()
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.iv_user);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
