package com.codepath.flickster.activities;

import android.os.Bundle;

import com.codepath.flickster.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by qiming on 7/23/2016.
 */
public class QuickPlayActivity extends YouTubeBaseActivity {
//    @BindView(R.id.player)
    YouTubePlayerView player;
    //R.layout.activity_quick_play

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
//        ButterKnife.bind(this);
        setContentView(R.layout.activity_quick_play);
        player = (YouTubePlayerView) findViewById(R.id.player);

        player.initialize("275996572656-01ui6dq8q5mnj4i8bas97p024bpmqaa9.apps.googleusercontent.com",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        youTubePlayer.cueVideo("5xVh-7ywKpE");
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }
}
