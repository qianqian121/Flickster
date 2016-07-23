package com.codepath.flickster.adapters;

import android.support.annotation.Nullable;
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
public class PopularMovieViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.ivBackDrop)
    ImageView ivBackDrop;
    @Nullable
    @BindView(R.id.tvTitlePopular)
    TextView tvTitlePopular;
    @Nullable
    @BindView(R.id.tvOverviewPopular)
    TextView tvOverviewPopular;

    //R.layout.item_popular

    public PopularMovieViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
