package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MovieDetailActivity extends AppCompatActivity {


    private ImageView mMoviePosterImage;
    private ImageView mMovieBackdropImage;
    private RatingBar mUserRating;
    private TextView tvMovieTitle;
    private TextView tvMovieOverview;
    private TextView tvMovieReleaseDate;
    private String sMovieUserRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_poster_item);

        mMoviePosterImage = findViewById(R.id.movie_poster_image);
        mMovieBackdropImage = findViewById(R.id.movie_backdrop_image);
        mUserRating = findViewById(R.id.movie_user_rating);
        tvMovieTitle = findViewById(R.id.movie_title);
        tvMovieOverview = findViewById(R.id.movie_overview);
//        tvMovieUserRating = findViewById(R.id.movie_user_rating);
        tvMovieReleaseDate = findViewById(R.id.movie_release_date);


        Intent intent = getIntent();

        String backdropImageUrl = intent.getStringExtra("backdrop");
        String moviePosterImageUrl = intent.getStringExtra("posterImage");

        Picasso.with(this)
                .load(backdropImageUrl)
                .placeholder(R.color.colorAccent)
                .into(mMovieBackdropImage);

        Picasso.with(this)
                .load(moviePosterImageUrl)
                .placeholder(R.color.colorAccent)
                .into(mMoviePosterImage);

        tvMovieTitle.setText(intent.getStringExtra("title"));
        tvMovieOverview.setText(intent.getStringExtra("description"));
//        tvMovieUserRating.setText("Rating: " + intent.getStringExtra("userRating"));
        // For converting a string into a rating...
        // https://stackoverflow.com/questions/16529640/how-to-set-value-to-rating-bar-with-string
        // Aso I wanted a RatingBar but having 10 stars made it hard to tell quickly what the
        // rating was so I divided the score by 2 and made it out of 5 stars
        sMovieUserRating = intent.getStringExtra("userRating");
        float rating = Float.parseFloat(sMovieUserRating);
        rating = (rating / 2);
        mUserRating.setRating(rating);
        Log.d("userRating2: ", String.valueOf(rating));
        tvMovieReleaseDate.setText(getString(R.string.released) + intent.getStringExtra("releaseDate"));

    }
}
