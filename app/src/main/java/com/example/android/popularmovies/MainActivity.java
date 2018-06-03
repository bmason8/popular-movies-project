package com.example.android.popularmovies;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import com.example.android.popularmovies.data.MovieDbHelper;
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

    private List<Movie> mMovieList;
    private SQLiteDatabase db;

    private static final String API_KEY = BuildConfig.MY_MOVIE_DB_API_KEY;
    private String getParameter;
    private static final String TOP_RATED = "top_rated";
    private static final String MOST_POPULAR = "popular";
    private static final String FAVOURITES = "favourites";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // https://www.youtube.com/watch?v=wcE7IIHKfRg
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                        getParameter = FAVOURITES;
                        fetchMovieList(getParameter);
                        return true;
                }
                return true;
            }
        });

        getParameter = MOST_POPULAR;
        mMovieList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.rv_movie_posters);
        fetchMovieList(getParameter);
        // https://stackoverflow.com/questions/40236786/set-initially-selected-item-index-id-in-bottomnavigationview/43278541
        bottomNavigationView.setSelectedItemId(R.id.settings_most_popular);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchMovieList(getParameter);
    }

    private void setUpRecyclerViewAndAdapters() {
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
    }

    private void fetchMovieList(String getParameter) {

        setUpRecyclerViewAndAdapters();

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

        MovieDbHelper mMovieDbHelper = new MovieDbHelper(this);
        db = mMovieDbHelper.getWritableDatabase();

        mMovieList.clear();
        String[] columns = {
                MovieDbEntry.COLUMN_ID_TMDB,
                MovieDbEntry.COLUMN_TITLE,
                MovieDbEntry.COLUMN_POSTER_PATH,
                MovieDbEntry.COLUMN_OVERVIEW,
                MovieDbEntry.COLUMN_VOTE_AVERAGE,
                MovieDbEntry.COLUMN_BACKDROP_PATH,
                MovieDbEntry.COLUMN_DATE,
                MovieDbEntry.COLUMN_FAVORITE,
        };

        String sortOrder = MovieDbEntry._ID;

        Cursor cursor = db.query(MovieDbEntry.TABLE_NAME,
                columns,
                null,
                null,
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
        db.close();

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