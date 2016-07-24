package com.codepath.flickster.activities;

import android.os.Bundle;
import android.util.Log;

import com.codepath.flickster.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by qiming on 7/23/2016.
 */
public class QuickPlayActivity extends YouTubeBaseActivity {
    @BindView(R.id.player)
    YouTubePlayerView player;
    AsyncHttpClient client;
    String trailerUrl;
    //R.layout.activity_quick_play

    String getTrailerSource(String id) {
        final String[] result = {""};


        return result[0];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        setContentView(R.layout.activity_quick_play);
        ButterKnife.bind(this);
//        player = (YouTubePlayerView) findViewById(R.id.player);
        String id = getIntent().getStringExtra("id");
        trailerUrl = String.format("https://api.themoviedb.org/3/movie/%s/trailers?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", id);
        client = new AsyncHttpClient();

        player.initialize("275996572656-01ui6dq8q5mnj4i8bas97p024bpmqaa9.apps.googleusercontent.com",
                new YouTubePlayer.OnInitializedListener() {
                    YouTubePlayer youTubePlayer;
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        final YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        // youTubePlayer.cueVideo("5xVh-7ywKpE");
                        this.youTubePlayer = youTubePlayer;

                        RequestParams params = new RequestParams();
                        params.put("q", "android");
                        params.put("rsz", "8");
                        client.get(trailerUrl, params, new JsonHttpResponseHandler() {

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                //super.onSuccess(statusCode, headers, response);
                                JSONArray movieJsonResults = null;
                                try {
                                    movieJsonResults = response.getJSONArray("youtube");
                                    String source = movieJsonResults.getJSONObject(0).getString("source");
                                    youTubePlayer.cueVideo(source);
                                    Log.d("tag", "onSuccess: ");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                super.onFailure(statusCode, headers, responseString, throwable);
                                Log.d("tag", "onFailure: ");
                            }
                        });
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }
}
