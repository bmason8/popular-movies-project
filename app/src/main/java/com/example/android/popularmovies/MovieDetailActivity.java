package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class MovieDetailActivity extends AppCompatActivity {

    private ImageView mMoviePosterImage;
    private ImageView mMovieBackdropImage;
    private TextView tvMovieTitle, tvMovieOverview, tvMovieUserRating, tvMovieReleaseDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_poster_item);

//        mMoviePosterImage = findViewById(R.id.movie_poster_image);

        mMovieBackdropImage = findViewById(R.id.movie_backdrop_image);
        tvMovieTitle = findViewById(R.id.movie_title);
        tvMovieOverview = findViewById(R.id.movie_overview);
        tvMovieUserRating = findViewById(R.id.movie_user_rating);
        tvMovieReleaseDate = findViewById(R.id.movie_release_date);


        Intent intent = getIntent();

        String backdropImageUrl = intent.getStringExtra("backdrop");

//        mMoviePosterImage.setImageResource(getIntent().getIntExtra("posterImage", 00));

//        mMovieBackdropImage.setImageResource(getIntent().getIntExtra("backdrop", 0));

        Toast.makeText(this, backdropImageUrl, Toast.LENGTH_SHORT).show();
        Log.d("backdropURL ", backdropImageUrl);

//        Picasso.with(this).load(backdropImageUrl).fit().centerInside().into(mMovieBackdropImage);
        // Need to fix the path to the image. Currently I'm only passing it the extension that is unique for the needed image. It needs to be combined with the default path.
        // Will look something like this --> http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
        Picasso.with(this)
                .load(backdropImageUrl)
                .placeholder(R.color.colorAccent)
                .into(mMovieBackdropImage);

        tvMovieTitle.setText(intent.getStringExtra("title"));
        tvMovieOverview.setText(intent.getStringExtra("description"));
        tvMovieUserRating.setText(intent.getStringExtra("userRating"));
        tvMovieReleaseDate.setText(intent.getStringExtra("releaseDate"));

    }
}
