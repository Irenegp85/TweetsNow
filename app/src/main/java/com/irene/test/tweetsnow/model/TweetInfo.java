package com.irene.test.tweetsnow.model;

import com.orm.SugarRecord;

/**
 * Class which represent the information necessary of a tweet
 *
 * Created by Irene on 06/04/2015.
 */
public class TweetInfo extends SugarRecord<TweetInfo> {
    private String DateCreated;
    private String Text;
    private int RetWeetCount;
    private String Name;
    private String ScreenName;
    private String Image;

    public TweetInfo(){}

    public TweetInfo(String d, String t, int c, String n, String s, String i){
        this.DateCreated = d;
        this.Text = t;
        this.RetWeetCount = c;
        this.Name = n;
        this.ScreenName = s;
        this.Image = i;
    }

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public int getRetWeetCount() {
        return RetWeetCount;
    }

    public void setRetWeetCount(int retWeetCount) {
        RetWeetCount = retWeetCount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getScreenName() {
        return ScreenName;
    }

    public void setScreenName(String screenName) {
        ScreenName = screenName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
