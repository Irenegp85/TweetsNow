package com.irene.test.tweetsnow.http;

/**
 * Created by Irene on 28/03/2015.
 */


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.irene.test.tweetsnow.ServiceGenerator;
import com.irene.test.tweetsnow.api.interfaces.TwitterClient;
import com.irene.test.tweetsnow.api.model.AccessToken;
import com.irene.test.tweetsnow.interfaces.AuxiliarAsyncFunction;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit.mime.TypedString;

/**
 * The function of this class is to connect to server and to download information
 *
 * Created by Irene on 28/03/2015.
 */

public class MyAsyncTask extends AsyncTask<Object, Integer, Boolean> {

    public final static int AUTH_PETITION = 0;
    public final static int GET_TWEETS_PETITION = 1;

    private Context context;
    private String url;
    private int type_petition;

    private Object ret;
    private AuxiliarAsyncFunction listener = null;


    public MyAsyncTask(Context context, String url, int type) {
        this.context = context;
        this.url = url;
        this.type_petition = type;
    }

    @Override
    protected Boolean doInBackground(Object... params) {

        try {
            TwitterClient twitterService = ServiceGenerator.createService(TwitterClient.class, url, null);
            switch(type_petition){
                case AUTH_PETITION:
                    if(params.length == 3){
                        ret = twitterService.authorizeUser(
                                String.valueOf(params[0]),
                                String.valueOf(params[1]),
                                (TypedString)params[2]);
                    }else{
                        return false;
                    }
                    break;
                case GET_TWEETS_PETITION:
                    if(params.length == 2){
                        ret = twitterService.fetchTweets(
                                String.valueOf(params[0]),
                                String.valueOf(params[1]));
                    }else{
                        return false;
                    }
                    break;
            }

        } catch (Exception e) {
            return false;
        }


        return true;

    }

    @Override
    protected void onPreExecute() {
        if (listener != null) {
            listener.OnPreExecute();
        } else {
            super.onPreExecute();
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (listener != null) {
            listener.OnPostExecute(result, ret);
        } else {
            super.onPostExecute(result);
        }
    }

    public void setFunctions(AuxiliarAsyncFunction listener) {
        this.listener = listener;
    }



}
