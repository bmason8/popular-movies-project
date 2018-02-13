package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.popularmovies.Movie.MovieResult;
import com.example.android.popularmovies.utilities.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MovieGridAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private MovieGridAdapter mAdapter;
    String baseBackDropUrl = "https://image.tmdb.org/t/p/w185";

    private List<Movie> mMovieList;

   public static final String API_KEY = "f406f3d6fecd4c4fcb9919780dc3954e";
   private String getParameter;
   private static final String TOP_RATED = "top_rated";
    private static final String MOST_POPULAR = "popular";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getParameter = MOST_POPULAR;

        mMovieList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.rv_movie_posters);

        fetchMovieList(getParameter);

    }

    private void fetchMovieList(String getParameter) {
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

        retrofit2.Call<Movie.MovieResult> call = apiInterface.getMovieResults(getParameter, API_KEY);

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(retrofit2.Call<Movie.MovieResult> call, Response<MovieResult> response) {
                Movie.MovieResult result = response.body();
                mAdapter.setmMovieList(result.getResults());
                mMovieList = result.getResults();
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
        intent.putExtra("posterImage", movie.getPoster());
        intent.putExtra("backdrop", baseBackDropUrl + movie.getBackdrop());
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("description", movie.getDescription());
        intent.putExtra("userRating", movie.getUserRating());
        intent.putExtra("releaseDate", movie.getReleaseDate());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.top_rated:
                Toast.makeText(this, "Top Rated", Toast.LENGTH_SHORT).show();
                getParameter = TOP_RATED;
                fetchMovieList(getParameter);
                return true;

            case R.id.most_popular:
                Toast.makeText(this, "Most Popular", Toast.LENGTH_SHORT).show();
                getParameter = MOST_POPULAR;
                fetchMovieList(getParameter);
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }
    }
}
