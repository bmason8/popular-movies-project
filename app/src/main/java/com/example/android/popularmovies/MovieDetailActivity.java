package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.adapters.ReviewsAdapter;
import com.example.android.popularmovies.model.Reviews;
import com.example.android.popularmovies.model.Reviews.ReviewsResult;
import com.example.android.popularmovies.model.Video;
import com.example.android.popularmovies.utilities.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieDetailActivity extends AppCompatActivity {

    // RatingBar
    @BindView(R.id.movie_user_rating)
    RatingBar mUserRating;

    // ImageViews
    @BindView(R.id.movie_poster_image)
    ImageView mMoviePosterImage;
    @BindView(R.id.movie_backdrop_image)
    ImageView mMovieBackdropImage;

    // TextViews
    @BindView(R.id.movie_title)
    TextView tvMovieTitle;
    @BindView(R.id.movie_overview)
    TextView tvMovieOverview;
    @BindView(R.id.movie_release_date)
    TextView tvMovieReleaseDate;
    @BindView(R.id.reviews)
    TextView tvReviews;
    @BindView(R.id.testVideo)
    TextView tvTestVideo;

    // ImageButtons
    @BindView(R.id.trailer)
    ImageButton mImageButton;

//    @BindView(R.id.movie_user_rating)
//    TextView tvMovieUserRating;
    private static final String API_KEY = BuildConfig.MY_MOVIE_DB_API_KEY;
    private String sMovieUserRating;
    private List<Video> mVideo;
    private List<Reviews> mReviews;

    // RecyclerView
    private RecyclerView recyclerView;
//    private ReviewsAdapter adapter;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_poster_item);
        ButterKnife.bind(this);

        recyclerView = findViewById(R.id.rv_movie_reviews);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mVideo = new ArrayList<>();
        mReviews = new ArrayList<>();

        Intent intent = getIntent();

        int movie_id = intent.getIntExtra("id", 0);
        Log.d("movieID: ", String.valueOf(movie_id));

        adapter = new ReviewsAdapter(mReviews, this);
        recyclerView.setAdapter(adapter);

        // Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.MOVIE_DB_BASE_MOVIE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<ReviewsResult> call = apiInterface.getMovieReviews(movie_id, API_KEY);

        call.enqueue(new Callback<ReviewsResult>() {
            @Override
            public void onResponse(Call<Reviews.ReviewsResult> call, Response<ReviewsResult> response) {
                Reviews.ReviewsResult result = response.body();
                mReviews = result.getResults();
            }

            @Override
            public void onFailure(Call<Reviews.ReviewsResult> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("Fail: ", t.getMessage());
            }
        });

//        Call<VideoResults> call = apiInterface.getMovieVideos(movie_id, API_KEY);
//        call.enqueue(new Callback<VideoResults>() {
//            @Override
//            public void onResponse(Call<VideoResults> call, Response<VideoResults> response) {
//                VideoResults result = response.body();
//                mVideo = result.getResults();
//            }
//
//            @Override
//            public void onFailure(Call<VideoResults> call, Throwable t) {
//                Toast.makeText(MovieDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//                Log.d("Fail: ", t.getMessage());
//            }
//        });

        String backdropImageUrl = intent.getStringExtra("backdrop");
        String moviePosterImageUrl = intent.getStringExtra("posterImage");

        Picasso.with(this)
                .load(backdropImageUrl)
                .placeholder(R.drawable.no_image_error)
                .error(R.drawable.no_image_error)
                .into(mMovieBackdropImage);

        Picasso.with(this)
                .load(moviePosterImageUrl)
                .placeholder(R.color.colorPrimary)
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
//        Log.d("userRating2: ", String.valueOf(rating));
        tvMovieReleaseDate.setText(getString(R.string.released) + intent.getStringExtra("releaseDate"));
//        List<Reviews> temp = intent.getParcelableArrayListExtra("reviews");
//        tvTestVideo.setText(mVideo.toString());
//        Log.d("testVideo: ", mVideo.toString());
        tvReviews.setText(mReviews.toString());
        Log.d("reviews", mReviews.toString());
    }
}
