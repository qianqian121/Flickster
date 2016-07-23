package com.codepath.flickster.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiming on 7/21/2016.
 */
public class MovieViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvOverview)
    TextView tvOverview;
    @BindView(R.id.ivMovieImage)
    ImageView ivMovieImage;

    public MovieViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
