package com.irene.test.tweetsnow.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Class with the necessary info of the tweet's user
 *
 * Created by Irene on 31/03/2015.
 *
 */
public class TwitterUser {

    @SerializedName("created_at")
    private String DateCreated;

    @SerializedName("id")
    private String Id;

    @SerializedName("name")
    private String Name;

    @SerializedName("screen_name")
    private String ScreenName;

    @SerializedName("profile_image_url")
    private String Image;

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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getScreenName() {
        return ScreenName;
    }

    public void setScreenName(String screenName) {
        ScreenName = screenName;
    }
}
