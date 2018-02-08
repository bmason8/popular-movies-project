package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.popularmovies.utilities.ApiInterface;
import com.example.android.popularmovies.utilities.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MovieGridAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private MovieGridAdapter mAdapter;

    private List<Movie> mMovieList;

   public final static String API_KEY = "f406f3d6fecd4c4fcb9919780dc3954e";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieList = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_posters);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mAdapter = new MovieGridAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(MainActivity.this);
        List<Movie> movies = new ArrayList<>();

        for (int i=0; i< 25; i++) {
            movies.add(new Movie());
        }
        mAdapter.setmMovieList(movies);


// Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.MOVIE_DB_BASE_MOVIE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        retrofit2.Call<Movie.MovieResult> call = apiInterface.getMovieResults(API_KEY);

        call.enqueue(new Callback<Movie.MovieResult>() {
            @Override
            public void onResponse(retrofit2.Call<Movie.MovieResult> call, Response<Movie.MovieResult> response) {
                Movie.MovieResult result = response.body();
                mAdapter.setmMovieList(result.getResults());
                mMovieList = result.getResults();


//                Log.d("LOG: Page ",result.getPage());
//                Log.d("LOG: Total Results ",result.getTotal_results());
//                Log.d("LOG: Total Pages  ",result.getTotal_pages());
//                Log.d(" LOG:test ", listString);
//                Log.d(" LOG:Results ", String.valueOf(result.getResults()));
            }

            @Override
            public void onFailure(retrofit2.Call<Movie.MovieResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("Fail: ",t.getMessage());
            }
        });

    }

    @Override
    public void onItemClick(int position) {
//        Movie movie = mMovieList.get(getAdapterPosition());
        Movie movie = mMovieList.get(position);
        Intent intent = new Intent(this, MovieDetailActivity.class);
//            intent.putExtra("posterImage", movie.getPoster());
        intent.putExtra("backdrop", movie.getBackdrop());
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("description", movie.getDescription());
        intent.putExtra("userRating", movie.getUserRating());
        intent.putExtra("releaseDate", movie.getReleaseDate());
        startActivity(intent);
    }
}
