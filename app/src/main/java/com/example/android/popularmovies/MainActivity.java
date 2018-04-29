package com.example.android.popularmovies;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.popularmovies.adapters.MovieGridAdapter;
import com.example.android.popularmovies.adapters.MovieGridAdapter.MovieGridAdapterOnClickHandler;
import com.example.android.popularmovies.data.MovieDatabase;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.Movie.MovieResult;
import com.example.android.popularmovies.utilities.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Helpful Resources...
//        https://www.journaldev.com/13639/retrofit-android-example-tutorial

public class MainActivity extends AppCompatActivity implements MovieGridAdapterOnClickHandler {
    private RecyclerView mRecyclerView;
    private MovieGridAdapter mAdapter;
    public static MovieDatabase movieDatabase;
    final String baseBackDropUrl = "https://image.tmdb.org/t/p/w500";

    private List<Movie> mMovieList;

    private static final String API_KEY = BuildConfig.MY_MOVIE_DB_API_KEY;
    private String getParameter;
    private static final String TOP_RATED = "top_rated";
    private static final String MOST_POPULAR = "popular";
    private static final String VIDEOS = "videos";
    private static final String REVIEWS = "reviews";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieDatabase = Room.databaseBuilder(getApplicationContext(), MovieDatabase.class, "movieDatabase").allowMainThreadQueries().build();

        getParameter = MOST_POPULAR;

        mMovieList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.rv_movie_posters);

        fetchMovieList(getParameter);

    }

    private void fetchFavourites() {

        // set a LayoutManager on the RecyclerView
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        // mRecyclerView.setHasFixedSize(true);
        // create a new MovieGridAdapter and give it context
        mAdapter = new MovieGridAdapter(this);
        // set the new MovieGridAdapter on the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

//        mAdapter.setmMovieList(mMovieList);

        // set the onClickListener from MovieDetailActivity on the adapter
        mAdapter.MovieGridAdapterClickListener(MainActivity.this);
        List<Movie> movies = new ArrayList<>();
        mAdapter.setmMovieList(movies);

        // query the local database for movies and pass them to the mAdapter and mMovieList
        movies = movieDatabase.movieDao().getMovies();

        mAdapter.setmMovieList(movies);
        mMovieList = movies;


    }

    private void fetchMovieList(String getParameter) {
        // set a LayoutManager on the RecyclerView
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        // mRecyclerView.setHasFixedSize(true);
        // create a new MovieGridAdapter and give it context
        mAdapter = new MovieGridAdapter(this);
        // set the new MovieGridAdapter on the RecyclerView
        mRecyclerView.setAdapter(mAdapter);
        // set the onClickListener from MovieDetailActivity on the adapter
        mAdapter.MovieGridAdapterClickListener(MainActivity.this);
        List<Movie> movies = new ArrayList<>();
        mAdapter.setmMovieList(movies);

        // Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.MOVIE_DB_BASE_MOVIE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<MovieResult> call = apiInterface.getMovieResults(getParameter, API_KEY);
        Log.d("call ", call.toString());

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<Movie.MovieResult> call, Response<MovieResult> response) {
                Movie.MovieResult result = response.body();
                mAdapter.setmMovieList(result.getResults());
                mMovieList = result.getResults();
            }

            @Override
            public void onFailure(Call<Movie.MovieResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("Fail: ", t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Movie movie = mMovieList.get(position);
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("id", movie.getId());
        intent.putExtra("posterImage", movie.getPoster());
        intent.putExtra("backdrop", movie.getBackdrop());
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("description", movie.getDescription());
        intent.putExtra("userRating", movie.getUserRating());
        intent.putExtra("releaseDate", movie.getReleaseDate());

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.settings_top_rated:
                getParameter = TOP_RATED;
                fetchMovieList(getParameter);
                return true;

            case R.id.settings_most_popular:
                getParameter = MOST_POPULAR;
                fetchMovieList(getParameter);
                return true;
            case R.id.settings_favourites:
                fetchFavourites();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}