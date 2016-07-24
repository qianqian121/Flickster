package com.codepath.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.flickster.R;
import com.codepath.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by qiming on 7/21/2016.
 */
public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Movie> mMovies;
    private final int POPULAR = 1;
    private View.OnClickListener mOnClickListener;
    private View.OnClickListener mPopularOnClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setPopularOnClickListener(View.OnClickListener onClickListener) {
        this.mPopularOnClickListener = onClickListener;
    }

    public MovieAdapter(List<Movie> movies) {
        mMovies = movies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        RecyclerView.ViewHolder movieViewHolder;
        if (viewType == POPULAR) {
            View movieView = inflater.inflate(R.layout.item_popular, parent, false);
            movieView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPopularOnClickListener.onClick(view);
                }
            });
            movieViewHolder = new PopularMovieViewHolder(movieView);
        }else {
            View movieView = inflater.inflate(R.layout.item_movie, parent, false);
            movieView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnClickListener.onClick(view);
                }
            });
            movieViewHolder = new MovieViewHolder(movieView);
        }
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        if (movie.isPopular()) {
            PopularMovieViewHolder vh = (PopularMovieViewHolder) holder;
            Picasso.with(vh.itemView.getContext()).load(movie.getBackDropPath()).transform(new RoundedCornersTransformation(10, 10)).placeholder(R.drawable.play).into(vh.ivBackDrop);
            int orientation = vh.itemView.getContext().getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (vh.tvTitlePopular != null) {
                    vh.tvTitlePopular.setText(movie.getOriginalTitle());
                }
                if (vh.tvOverviewPopular != null) {
                    vh.tvOverviewPopular.setText(movie.getOverview());
                }
                // ...
            }
        } else {
            MovieViewHolder vh = (MovieViewHolder) holder;
            vh.tvTitle.setText(movie.getOriginalTitle());
            vh.tvOverview.setText(movie.getOverview());
            String image = "";
            int orientation = vh.itemView.getContext().getResources().getConfiguration().orientation;
            int placeHolder = R.drawable.picasso;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                image = movie.getPosterPath();
                // ...
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                image = movie.getBackDropPath();
                placeHolder = R.drawable.play;
                // ...
            }
            Picasso.with(vh.itemView.getContext()).load(image).transform(new RoundedCornersTransformation(10, 10)).placeholder(placeHolder).into(vh.ivMovieImage);
            //Picasso.with(vh.ivMovieImage.getContext()).load(movie.getPosterPath()).into(vh.ivMovieImage);
            //Picasso.with(vh.ivBackDropLand.getContext()).load(movie.getBackDropPath()).into(vh.ivBackDropLand);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Movie movie = mMovies.get(position);
        if (movie.isPopular())
            return POPULAR;

        return 0;
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (mMovies != null) {
            return mMovies.size();
        }
        return 0;
    }

    public void clear() {
        mMovies.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Movie> list) {
        mMovies.addAll(list);
        notifyDataSetChanged();
    }
}
