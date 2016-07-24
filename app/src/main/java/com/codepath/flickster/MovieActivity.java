package com.codepath.flickster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.codepath.flickster.activities.DetailActivity;
import com.codepath.flickster.activities.QuickPlayActivity;
import com.codepath.flickster.adapters.MovieAdapter;
import com.codepath.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {
    ArrayList<Movie> movies;
    @BindView(R.id.rvItems)
    RecyclerView rvItems;
    MovieAdapter movieAdapter;
    AsyncHttpClient client;
    final static String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    String getTrailerSource(String id) {
        final String[] result = {""};
        String trailerUrl = String.format("https://api.themoviedb.org/3/movie/%s/trailers?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", id);
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                JSONArray movieJsonResults = null;
                try {
                    movieJsonResults = response.getJSONArray("youtube");
                    result[0] = movieJsonResults.getJSONObject(0).getString("source");
                    Log.d("tag", "onSuccess: ");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("tag", "onSuccess: ");
            }
        });
        while (result[0].length() == 0)
            ;
        return result[0];
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        movies = new ArrayList<>();
        movieAdapter = new MovieAdapter(movies);
        movieAdapter.setPopularOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieActivity.this, QuickPlayActivity.class);
                int position = rvItems.getChildAdapterPosition(view);
                Movie movie = movies.get(position);
                String source = movie.getSource();
                if (source.length() == 0) {
                    source = getTrailerSource(movie.getId());
                    movie.setSource(source);
                }
                intent.putExtra("source", source);
                startActivity(intent);
            }
        });
        movieAdapter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieActivity.this, DetailActivity.class);
                int position = rvItems.getChildAdapterPosition(view);
                intent.putExtra("movie", movies.get(position));
                startActivity(intent);
            }
        });
        rvItems.setAdapter(movieAdapter);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("q", "android");
        params.put("rsz", "8");
        client.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                JSONArray movieJsonResults = null;
                try {
                    movieJsonResults = response.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(movieJsonResults));
                    movieAdapter.notifyDataSetChanged();
                    Log.d("tag", "onSuccess: ");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("tag", "onSuccess: ");
            }
        });
    }

    public void fetchTimelineAsync(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        client.get(url, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Remember to CLEAR OUT old items before appending in the new ones
                movieAdapter.clear();
                // ...the data has come back, add new items to your adapter...
                JSONArray movieJsonResults = null;
                try {
                    movieJsonResults = response.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                movieAdapter.addAll(Movie.fromJSONArray(movieJsonResults));
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);
            }

            public void onFailure(Throwable e) {
                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
            }
        });
    }
}
