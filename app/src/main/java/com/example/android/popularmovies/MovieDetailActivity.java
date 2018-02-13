package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MovieDetailActivity extends AppCompatActivity {

    private ImageView mMoviePosterImage;
    private ImageView mMovieBackdropImage;
    private TextView tvMovieTitle, tvMovieOverview, tvMovieUserRating, tvMovieReleaseDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_poster_item);

        mMoviePosterImage = findViewById(R.id.movie_poster_image);
        mMovieBackdropImage = findViewById(R.id.movie_backdrop_image);
        tvMovieTitle = findViewById(R.id.movie_title);
        tvMovieOverview = findViewById(R.id.movie_overview);
        tvMovieUserRating = findViewById(R.id.movie_user_rating);
        tvMovieReleaseDate = findViewById(R.id.movie_release_date);


        Intent intent = getIntent();

        String backdropImageUrl = intent.getStringExtra("backdrop");
        String moviePosterImageUrl = intent.getStringExtra("posterImage");

//        mMoviePosterImage.setImageResource(getIntent().getIntExtra("posterImage", 0));

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
        tvMovieUserRating.setText(intent.getStringExtra("userRating"));
        tvMovieReleaseDate.setText(intent.getStringExtra("releaseDate"));

    }
}
