package com.irene.test.tweetsnow;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.irene.test.tweetsnow.adapter.MyAdapter;
import com.irene.test.tweetsnow.api.model.AccessToken;
import com.irene.test.tweetsnow.api.model.Tweet;
import com.irene.test.tweetsnow.api.model.TweetsResponse;
import com.irene.test.tweetsnow.custom.FixedRecyclerView;
import com.irene.test.tweetsnow.http.MyAsyncTask;
import com.irene.test.tweetsnow.interfaces.AuxiliarAsyncFunction;
import com.irene.test.tweetsnow.model.TweetInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;
import retrofit.mime.TypedString;


public class MainActivity extends ActionBarActivity {

    private static final String API_URL = "https://api.twitter.com/";
    private static final String QUERY_BASE = "\"It%27s%20[TIME]%20and\"%20OR%20\"It%20is%20[TIME]%20and\"";
    private static final String QUERY_CONNECTOR = "%20OR%20";
    private static final String QUERY_PARAM_REPLACE = "[TIME]";
    private static final String TAG = "MainActivity";
    public static String TWEET_API_KEY = "MYegfadtMuicoYreESm4g";
    public static String TWEET_API_SECRET = "elINfmXhXXcS1xO5TIZPcobvACzWbDpKeeJJb5KzE";
    public static String BEARER = "";

    /**
     * Context
     */
    private Context ctxt;

    /**
     * Variable to indicate if the app is processing anything
     */
    private boolean isProcessing = false;

    /**
     * Views to show the information
     */
    private SwipeRefreshLayout srl_items;
    private FixedRecyclerView mRecyclerView;
    private FixedRecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    /**
     * Token to do the query
     */
    private AccessToken auth;

    /**
     * Variables which indicate the time needed in the query
     */
    private String strSearchingTime;
    private String strSearchingTime24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctxt = this;

        mRecyclerView = (FixedRecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        srl_items = (SwipeRefreshLayout) findViewById(R.id.srl_items);
        srl_items.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

        initTweets();
        addListeners();


    }

    /**
     * Initial function to show preloading tweets and loading new tweets
     */
    private void initTweets(){

        ArrayList<TweetInfo> tweets = (ArrayList<TweetInfo>) TweetInfo.find(TweetInfo.class, null, null, null, "id DESC", null);
        showTweets(tweets);
        srl_items.setRefreshing(true);
        getAuth();

    }

    /**
     * Function to init the listeners
     */
    private void addListeners(){
        srl_items.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (auth != null && auth.getTokenType().equals("bearer")) {
                    // Authenticate API requests with bearer token
                    getTweets(auth.getAccessToken());
                }else{
                    getAuth();
                }
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * This function authenticates the user and get the token to do the query
     */
    private void getAuth(){

        BEARER = TWEET_API_KEY + ":" + TWEET_API_SECRET;

        String base64Encoded = Base64.encodeToString(BEARER.getBytes(), Base64.NO_WRAP);

        TypedString typedString = new TypedString("grant_type=client_credentials");
        final MyAsyncTask mat = new MyAsyncTask(ctxt, API_URL, MyAsyncTask.AUTH_PETITION);
        mat.setFunctions(new AuxiliarAsyncFunction() {

            @Override
            public void OnPreExecute() {
                // TODO Auto-generated method stub

            }

            @Override
            public void OnPostExecute(Boolean result, Object ret) {

                if (result) {
                    auth = (AccessToken) ret;
                    if (auth != null && auth.getTokenType().equals("bearer")) {
                        // Authenticate API requests with bearer token
                        getTweets(auth.getAccessToken());
                    }else{

                        srl_items.setRefreshing(false);
                        showMessage(getString(R.string.main_error_server_petition));
                    }

                } else {
                    srl_items.setRefreshing(false);
                    showMessage(getString(R.string.main_error_server_petition));
                }
                isProcessing = false;


            }
        });
        mat.execute("Basic " + base64Encoded,
                String.valueOf(typedString.length()),
                typedString);
    }

    /**
     * This function gets the tweets for a given query
     * @param accessToken Correct token to do the query
     */
    private void getTweets(String accessToken){


        final MyAsyncTask mat = new MyAsyncTask(ctxt, API_URL, MyAsyncTask.GET_TWEETS_PETITION);
        mat.setFunctions(new AuxiliarAsyncFunction() {

            @Override
            public void OnPreExecute() {
                // TODO Auto-generated method stub

            }

            @Override
            public void OnPostExecute(Boolean result, Object ret) {

                if (result) {
                    TweetsResponse status = (TweetsResponse) ret;

                    ArrayList<TweetInfo> tweets = new ArrayList<TweetInfo>();

                    for(int i = 0; i < status.getTweets().size(); i++){
                        Tweet t = status.getTweets().get(i);
                        String strText = correctText(t.getText());
                        TweetInfo ti = new TweetInfo(t.getDateCreated(), strText, t.getRetWeetCount(), t.getUser().getName(), t.getUser().getScreenName(), t.getUser().getImage());
                        ti.save();
                        tweets.add(ti);
                    }

                    showTweets(tweets);

                    srl_items.setRefreshing(false);


                } else {
                    srl_items.setRefreshing(false);
                    showMessage(getString(R.string.main_error_server_petition));
                }
                isProcessing = false;


            }
        });
        mat.execute("Bearer " + accessToken, getQuery());
    }

    /**
     * This function gets the correct form of text.
     * The text has to start with It is or It's
     * @param text Text to correct with de correct format
     */
    private String correctText(String text){
        int intIndexOf = 0;
        String strText;

        intIndexOf = text.toLowerCase().indexOf("it's " + strSearchingTime);
        if(intIndexOf<0) intIndexOf = text.toLowerCase().indexOf("it is " + strSearchingTime);
        if(intIndexOf<0) intIndexOf = text.toLowerCase().indexOf("it's " + strSearchingTime24);
        if(intIndexOf<0) intIndexOf = text.toLowerCase().indexOf("it is " + strSearchingTime24);

        if(intIndexOf>0) {
            strText = text.substring(intIndexOf);
            strText = strText.toUpperCase().charAt(0) + strText.substring(1);
        }else strText = text;

        return strText;
    }

    /**
     * This function builds the query to get tweets for a current time.
     */
    private String getQuery(){
        String query = new String();
        Calendar c = Calendar.getInstance();

        String strTime = "";
        String strTimeAmPm = "";

        String strMinute = c.get(Calendar.MINUTE)+"";
        if(c.get(Calendar.MINUTE)<10) strMinute = "0" + strMinute;

        String strHour = c.get(Calendar.HOUR)+"";
        if(c.get(Calendar.HOUR)<10) strHour = "0" + strHour;

        //AM
        strTime = strHour+":"+strMinute;
        strSearchingTime = c.get(Calendar.HOUR)+":"+strMinute;
        strTimeAmPm = strTime+" AM";

        if(c.get(Calendar.HOUR)!=c.get(Calendar.HOUR_OF_DAY)){
            //PM
            strTime = c.get(Calendar.HOUR_OF_DAY)+":"+strMinute;
            strSearchingTime24 = strTime;
            strTimeAmPm = c.get(Calendar.HOUR)+":"+strMinute+" PM";
        }

        query = QUERY_BASE.replace(QUERY_PARAM_REPLACE,strTime) + QUERY_CONNECTOR + QUERY_BASE.replace(QUERY_PARAM_REPLACE,strTimeAmPm) + QUERY_CONNECTOR + QUERY_BASE.replace(QUERY_PARAM_REPLACE,strTimeAmPm.replace(" ", ""));

        return query;
    }


    /**
     * This functions sends the tweets to the adapter for displaying.
     * @param tweets tweets to display
     */
    private void showTweets(ArrayList<TweetInfo> tweets){


        // specify an adapter (see also next example)
        ArrayList<TweetInfo> itemsDefault = new ArrayList<TweetInfo>(tweets);

        Collections.sort(itemsDefault, new Comparator<TweetInfo>() {

            public int compare(TweetInfo o1, TweetInfo o2) {
                int intRestComp = o2.getRetWeetCount() - o1.getRetWeetCount();
                if(intRestComp != 0)  {
                    SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
                    format.setLenient(true);

                    try {
                        Date newDate1 = format.parse(o1.getDateCreated());
                        Date newDate2 = format.parse(o2.getDateCreated());
                        intRestComp = (int)(newDate1.getTime() - newDate2.getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return intRestComp;
            }
        });
        mAdapter = new MyAdapter(itemsDefault, ctxt);

        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        mRecyclerView.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));
    };



    /**
     * This functions show a message
     * @param message Message to show
     */
    private void showMessage(String message){
        Toast.makeText(ctxt, message, Toast.LENGTH_LONG).show();
    }
}
