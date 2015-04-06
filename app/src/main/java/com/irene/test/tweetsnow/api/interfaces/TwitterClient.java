package com.irene.test.tweetsnow.api.interfaces;

import com.irene.test.tweetsnow.api.model.AccessToken;
import com.irene.test.tweetsnow.api.model.TweetsResponse;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.mime.TypedString;

/**
 * Interface with the functions used to get the information of authentication and tweets
 *
 * Created by Irene on 31/03/2015.
 */
public interface TwitterClient {
    @GET("/1.1/search/tweets.json")
    TweetsResponse fetchTweets(
            @Header("Authorization") String authorization,
            @Query("q") String query);



    @Headers({ "Content-Type: application/x-www-form-urlencoded;charset=UTF-8"})
    @POST("/oauth2/token")
    AccessToken authorizeUser(
            @Header("Authorization") String authorization,
            @Header("Content-Length") String bodyLength,
            @Body TypedString grantType);


}
