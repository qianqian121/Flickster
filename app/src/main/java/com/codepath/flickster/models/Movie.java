package com.codepath.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by qiming on 7/20/2016.
 */
public class Movie implements Serializable {
    private final static int POPULAR_VOTE = 5;

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackDropPath() {
        return String.format("https://image.tmdb.org/t/p/w780/%s", backDropPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    String posterPath;
    String backDropPath;
    String originalTitle;
    String overview;
    String id;
    String source;

    public String getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRealeaseDate() {
        return realeaseDate;
    }

    String realeaseDate;

    public float getVoteAverage() {
        return voteAverage;
    }

    float voteAverage;

    public boolean isPopular() {
        //return false;
        return (int)voteAverage >= POPULAR_VOTE;
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getString("id");
        this.posterPath = jsonObject.getString("poster_path");
        this.backDropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.realeaseDate = jsonObject.getString("release_date");
        this.voteAverage = Float.valueOf(jsonObject.getString("vote_average"));
        this.source = "";
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array) {
        ArrayList<Movie> result = new ArrayList<>();

        for (int x = 0; x < array.length(); x++) {
            try {
                result.add(new Movie(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
