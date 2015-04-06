package com.irene.test.tweetsnow.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Class to represents the info of user authenticated
 *
 * Created by Irene on 31/03/2015.
 */
public class AccessToken {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
