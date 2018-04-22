package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.adapters.ReviewListAdapter;
import com.example.android.popularmovies.adapters.VideoListAdapter;
import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.model.ReviewListResponse;
import com.example.android.popularmovies.model.Video;
import com.example.android.popularmovies.model.VideoListResponse;
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

//    @BindView(R.id.movie_user_rating)
//    TextView tvMovieUserRating;
    private static final String API_KEY = BuildConfig.MY_MOVIE_DB_API_KEY;
    private String sMovieUserRating;
    private List<Video> mVideoList = new ArrayList<>();
    private List<Review> mReviewList = new ArrayList<>();
    int movie_id;

    // RecyclerView
    private RecyclerView mRecyclerView;
    private ReviewListAdapter mReviewListAdapter;
    private VideoListAdapter mVideoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_poster_item);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        movie_id = intent.getIntExtra("id", 0);
        Log.d("movieID: ", String.valueOf(movie_id));

        initiateVideoListRecyclerView();
        initiateReviewListRecyclerView();
        loadMovieTrailers();
        loadReviews();

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
    }

    private void loadMovieTrailers(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.MOVIE_DB_BASE_MOVIE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<VideoListResponse> call = apiInterface.getMovieVideos(movie_id, API_KEY);

        call.enqueue(new Callback<VideoListResponse>() {
            @Override
            public void onResponse(@NonNull Call<VideoListResponse> call, @NonNull Response<VideoListResponse> response) {
                VideoListResponse result = response.body();
                // TODO insert a check for null and handle if response is null
                if (result != null) {
                    List<Video> videoList = result.getVideoList();
                    mVideoListAdapter.setData(videoList);
                } else {
                    // TODO show something instead
                    Log.d("movieTrailerResponse", "No movie trailers available");
                }
            }

            @Override
            public void onFailure(@NonNull Call<VideoListResponse> call, @NonNull Throwable t) {
                Toast.makeText(MovieDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("Fail: ", t.getMessage());
            }
        });
    }

    private void loadReviews(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.MOVIE_DB_BASE_MOVIE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<ReviewListResponse> call = apiInterface.getMovieReviews(movie_id, API_KEY);

        call.enqueue(new Callback<ReviewListResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewListResponse> call, @NonNull Response<ReviewListResponse> response) {
                ReviewListResponse result = response.body();
                // TODO insert a check for null and handle if response is null
                List<Review> reviewList = result.getmReviewList();
                mReviewListAdapter.setmReviewList(reviewList);
            }

            @Override
            public void onFailure(@NonNull Call<ReviewListResponse> call, @NonNull Throwable t) {
                Toast.makeText(MovieDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("Fail: ", t.getMessage());
            }
        });
    }

    private void initiateReviewListRecyclerView(){

        // https://medium.com/@szholdiyarov/how-to-add-divider-to-list-and-recycler-views-858344450401
        // used for putting the dividing line between recyclerView items
        mRecyclerView = findViewById(R.id.rv_movie_reviews);
        DividerItemDecoration itemDecor = new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecor);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mReviewListAdapter = new ReviewListAdapter(getBaseContext(), mReviewList);
        mRecyclerView.setAdapter(mReviewListAdapter);
        mReviewListAdapter.setmReviewList(mReviewList);
    }

    private void initiateVideoListRecyclerView(){

        mRecyclerView = findViewById(R.id.rv_movie_trailers);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mVideoListAdapter = new VideoListAdapter(getBaseContext(), mVideoList);
        mRecyclerView.setAdapter(mVideoListAdapter);
        mVideoListAdapter.setData(mVideoList);
    }

}
