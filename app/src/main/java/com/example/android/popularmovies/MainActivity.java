package com.example.android.popularmovies;

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

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MovieGridAdapter mAdapter;

   public final static String API_KEY = "   ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_posters);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mAdapter = new MovieGridAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
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


//                Log.d("LOG: Page ",result.getPage());
//                Log.d("LOG: Total Results ",result.getTotal_results());
//                Log.d("LOG: Total Pages  ",result.getTotal_pages());
//                Log.d(" LOG:test ", listString);
//                Log.d(" LOG:Results ", String.valueOf(result.getResults()));
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(retrofit2.Call<Movie.MovieResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("Fail: ",t.getMessage());
            }
        });







    }

    // ViewHolder
    public static class MovieGridViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public MovieGridViewHolder(View itemView){
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.movie_image);
        }
    }
}
