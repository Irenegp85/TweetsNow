package com.irene.test.tweetsnow.api.model;

import com.google.gson.annotations.SerializedName;
import com.irene.test.tweetsnow.api.model.Tweet;

import java.util.List;

/**
 * Class which represents a collection of tweets
 *
 * Created by Irene on 31/03/2015.
 */
public class TweetsResponse {
    @SerializedName("statuses")
    private List<Tweet> tweets;

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }
}
