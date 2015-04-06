package com.irene.test.tweetsnow;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Class responsible for connecting to the server
 *
 * Created by Irene on 31/03/2015.
 */
public class ServiceGenerator {
    private static final String TAG = "ServiceGenerator";
    private ServiceGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl, final String header) {
        Log.v(TAG, "createService");
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setConverter(new GsonConverter(gson));


        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }
}

