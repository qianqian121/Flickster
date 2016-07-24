package com.codepath.flickster.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by qiming on 7/23/2016.
 */
public class DetailActivity extends Activity {
    @BindView(R.id.ivDetailBackDrop)
    ImageView ivDetailBackDrop;
    @BindView(R.id.ivDetailPlayIcon)
    ImageView ivDetailPlayIcon;
    @BindView(R.id.flDetailBackDrop)
    FrameLayout flDetailBackDrop;
    @BindView(R.id.tvDetailTitle)
    TextView tvDetailTitle;
    @BindView(R.id.tvReleaseDate)
    TextView tvReleaseDate;
    @BindView(R.id.rbVote)
    RatingBar rbVote;
    @BindView(R.id.tvDetailOverview)
    TextView tvDetailOverview;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        movie = (Movie) getIntent().getSerializableExtra("movie");
        tvDetailTitle.setText(movie.getOriginalTitle());
        tvDetailOverview.setText(movie.getOverview());
        tvReleaseDate.setText("Release Date: " + movie.getRealeaseDate());
        rbVote.setRating((float) (5.0 * movie.getVoteAverage() / 9.0));
        Picasso.with(this).load(movie.getBackDropPath()).transform(new RoundedCornersTransformation(10, 10)).placeholder(R.drawable.picasso).into(ivDetailBackDrop);
    }

    public void onPlayMovie(View v) {
        Intent it = new Intent(DetailActivity.this, QuickPlayActivity.class);
        it.putExtra("id", movie.getId());
        startActivity(it);
    }
//    R.layout.activity_details
}
