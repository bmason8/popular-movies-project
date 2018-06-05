package com.example.android.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.popularmovies.adapters.MovieGridAdapter;
import com.example.android.popularmovies.adapters.MovieGridAdapter.MovieGridAdapterOnClickHandler;
import com.example.android.popularmovies.data.MovieContract.MovieDbEntry;
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

// Helpful Resources...
// https://www.journaldev.com/13639/retrofit-android-example-tutorial

public class MainActivity extends AppCompatActivity implements MovieGridAdapterOnClickHandler {
    private static final String LIFECYCLE_CALLBACKS_TEXT_KEY = "movieCallback";
    private static final String SHARED_PREFERENCES_TEXT_KEY = "userPreferences";
    private RecyclerView mRecyclerView;
    private MovieGridAdapter mAdapter;

    private List<Movie> mMovieList;
    private SQLiteDatabase db;

    private static final String API_KEY = BuildConfig.MY_MOVIE_DB_API_KEY;
    private static final String TOP_RATED = "top_rated";
    private static final String MOST_POPULAR = "popular";
    private static final String FAVOURITES = "favourites";
    private String getParameter;
    private int bottomNavPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_TEXT_KEY, 0);

        mRecyclerView = findViewById(R.id.rv_movie_posters);
        mMovieList = new ArrayList<>();

        // set a LayoutManager on the RecyclerView
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setHasFixedSize(true);
        // create a new MovieGridAdapter and give it context
        mAdapter = new MovieGridAdapter(this);
        // set the new MovieGridAdapter on the RecyclerView
        mRecyclerView.setAdapter(mAdapter);
        // set the onClickListener from MovieDetailActivity on the adapter
        mAdapter.MovieGridAdapterClickListener(MainActivity.this);
        mAdapter.setmMovieList(mMovieList);

        // https://www.youtube.com/watch?v=wcE7IIHKfRg
        bottomNavigationView.setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.settings_top_rated:
                        getParameter = TOP_RATED;
                        fetchMovieList(getParameter);
                        bottomNavPosition = 0;
                        return true;

                    case R.id.settings_most_popular:
                        getParameter = MOST_POPULAR;
                        fetchMovieList(getParameter);
                        bottomNavPosition = 1;
                        return true;

                    case R.id.settings_favourites:
                        getParameter = FAVOURITES;
                        fetchMovieList(getParameter);
                        bottomNavPosition = 2;
                        return true;
                }
                return true;
            }
        });

        if (savedInstanceState != null) {
            getParameter = sharedPreferences.getString("getParameter", TOP_RATED);
            bottomNavPosition = sharedPreferences.getInt("bottomNavPosition", 1);

            mMovieList = new ArrayList<>();
            // UPDATE mMovieList here with movieArray from savedInstanceState
            mMovieList = savedInstanceState.getParcelableArrayList(LIFECYCLE_CALLBACKS_TEXT_KEY);
            mAdapter.setmMovieList(mMovieList);

            // Don't think this is the absolute best way of doing this but working with bottomNavigationView was tricky
            // and so I solved what I needed by running this if else statement to update the position based on an int
            // that I saved in OnInstanceState
            if (bottomNavPosition == 0) {
                bottomNavigationView.setSelectedItemId(R.id.settings_top_rated);
            } else if (bottomNavPosition == 1) {
                bottomNavigationView.setSelectedItemId(R.id.settings_most_popular);
            } else if (bottomNavPosition == 2) {
                bottomNavigationView.setSelectedItemId(R.id.settings_favourites);
            }
        } else {

            if (getParameter == null) {
                getParameter = MOST_POPULAR;
                // https://stackoverflow.com/questions/40236786/set-initially-selected-item-index-id-in-bottomnavigationview/43278541
                bottomNavigationView.setSelectedItemId(R.id.settings_most_popular);
            }
            fetchMovieList(getParameter);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // https://stackoverflow.com/questions/12503836/how-to-save-custom-arraylist-on-android-screen-rotate
        outState.putParcelableArrayList(LIFECYCLE_CALLBACKS_TEXT_KEY, (ArrayList<? extends Parcelable>) mMovieList);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_TEXT_KEY, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("getParameter", getParameter);
        editor.putInt("bottomNavPosition", bottomNavPosition);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchMovieList(getParameter);
    }

    private void fetchMovieList(String getParameter) {

        if (getParameter == FAVOURITES) {
            mMovieList = getAllFavouriteMovies();
            mAdapter.setmMovieList(mMovieList);
        } else {

            // Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiInterface.MOVIE_DB_BASE_MOVIE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiInterface apiInterface = retrofit.create(ApiInterface.class);

            Call<MovieResult> call = apiInterface.getMovieResults(getParameter, API_KEY);
            Log.d("call", call.toString());

            call.enqueue(new Callback<MovieResult>() {
                @Override
                public void onResponse(@NonNull Call<Movie.MovieResult> call, @NonNull Response<MovieResult> response) {
                    Movie.MovieResult result = response.body();
                    if (result != null) {
                        mAdapter.setmMovieList(result.getResults());
                        mMovieList = result.getResults();
                    } else {
                        Log.d("failed", "failed to retrieve movie list");
                        Toast.makeText(MainActivity.this, R.string.failed_to_fetch_movies, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Movie.MovieResult> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("fail: ", t.getMessage());
                }
            });
        }
    }

    // https://www.youtube.com/watch?v=JJqVPKrL2e8&index=7&list=PLvPqrYVmSBHf3KhSUP8xHHcN5aMeGsWBl
    private List<Movie> getAllFavouriteMovies() {

        String sortOrder = MovieDbEntry._ID;

        mMovieList.clear();
        String[] columns = {
                MovieDbEntry.COLUMN_ID_TMDB,
                MovieDbEntry.COLUMN_TITLE,
                MovieDbEntry.COLUMN_POSTER_PATH,
                MovieDbEntry.COLUMN_OVERVIEW,
                MovieDbEntry.COLUMN_VOTE_AVERAGE,
                MovieDbEntry.COLUMN_BACKDROP_PATH,
                MovieDbEntry.COLUMN_DATE,
        };

        Cursor cursor = getContentResolver().query(MovieDbEntry.CONTENT_URI,
                columns,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(MovieDbEntry.COLUMN_ID_TMDB))));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieDbEntry.COLUMN_TITLE)));
                movie.setPoster(cursor.getString(cursor.getColumnIndex(MovieDbEntry.COLUMN_POSTER_PATH)));
                movie.setDescription(cursor.getString(cursor.getColumnIndex(MovieDbEntry.COLUMN_OVERVIEW)));
                movie.setUserRating(cursor.getString(cursor.getColumnIndex(MovieDbEntry.COLUMN_VOTE_AVERAGE)));
                movie.setBackdrop(cursor.getString(cursor.getColumnIndex(MovieDbEntry.COLUMN_BACKDROP_PATH)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(MovieDbEntry.COLUMN_DATE)));

                mMovieList.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return mMovieList;
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
}