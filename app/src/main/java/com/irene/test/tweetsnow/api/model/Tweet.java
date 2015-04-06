package com.irene.test.tweetsnow.api.model;

import com.google.gson.annotations.SerializedName;
import com.irene.test.tweetsnow.api.model.TwitterUser;

/**
 * Class with the necessary info of the tweets
 *
 * Created by Irene on 31/03/2015.
 */
public class Tweet {

    @SerializedName("created_at")
    private String DateCreated;

    @SerializedName("id")
    private String Id;

    @SerializedName("text")
    private String Text;

    @SerializedName("in_reply_to_status_id")
    private String InReplyToStatusId;

    @SerializedName("in_reply_to_user_id")
    private String InReplyToUserId;

    @SerializedName("in_reply_to_screen_name")
    private String InReplyToScreenName;

    @SerializedName("retweet_count")
    private int RetWeetCount;

    @SerializedName("user")
    private TwitterUser User;

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getInReplyToScreenName() {
        return InReplyToScreenName;
    }

    public void setInReplyToScreenName(String inReplyToScreenName) {
        InReplyToScreenName = inReplyToScreenName;
    }

    public String getInReplyToStatusId() {
        return InReplyToStatusId;
    }

    public void setInReplyToStatusId(String inReplyToStatusId) {
        InReplyToStatusId = inReplyToStatusId;
    }

    public String getInReplyToUserId() {
        return InReplyToUserId;
    }

    public void setInReplyToUserId(String inReplyToUserId) {
        InReplyToUserId = inReplyToUserId;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public TwitterUser getUser() {
        return User;
    }

    public int getRetWeetCount() {
        return RetWeetCount;
    }

    public void setRetWeetCount(int retWeetCount) {
        RetWeetCount = retWeetCount;
    }

    public void setUser(TwitterUser user) {
        User = user;
    }

    @Override
    public String toString() {


        return getText();
    }
}
