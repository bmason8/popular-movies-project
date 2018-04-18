package com.example.android.popularmovies.utilities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.android.popularmovies.BuildConfig;
import com.example.android.popularmovies.model.Video;
import com.example.android.popularmovies.model.VideoResults;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCalls extends AppCompatActivity {

    private static final String API_KEY = BuildConfig.MY_MOVIE_DB_API_KEY;
    private List<Video> mVideo;

    Intent intent = getIntent();

    int movie_id = intent.getIntExtra("id", 0);

    public void fetchVideos(){

        mVideo = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.MOVIE_DB_BASE_MOVIE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<VideoResults> call = apiInterface.getMovieVideos(movie_id, API_KEY);
        call.enqueue(new Callback<VideoResults>() {
            @Override
            public void onResponse(Call< VideoResults > call, Response<VideoResults> response) {
                VideoResults result = response.body();
                mVideo = result.getResults();
            }

            @Override
            public void onFailure(Call<VideoResults> call, Throwable t) {
                Toast.makeText(ApiCalls.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("Fail: ", t.getMessage());
            }
        });
    }


}
