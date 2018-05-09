package com.example.android.popularmovies;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import com.example.android.popularmovies.data.MovieDatabase;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.model.ReviewListResponse;
import com.example.android.popularmovies.model.Video;
import com.example.android.popularmovies.model.VideoListResponse;
import com.example.android.popularmovies.utilities.ApiInterface;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieDetailActivity extends AppCompatActivity {

    public static MovieDatabase movieDatabase;

    // RatingBar
    @BindView(R.id.movie_user_rating)
    RatingBar mUserRating;

    // ImageViews
    @BindView(R.id.movie_poster_image)
    ImageView mMoviePosterImage;
    @BindView(R.id.movie_backdrop_image)
    ImageView mMovieBackdropImage;
    // Floating Action Button
    @BindView(R.id.favourite_btn)
    FloatingActionButton mFavourite_btn;

    Movie currentMovie = new Movie();

    // TextViews
    @BindView(R.id.movie_title)
    TextView tvMovieTitle;
    @BindView(R.id.movie_overview)
    TextView tvMovieOverview;
    @BindView(R.id.movie_release_date)
    TextView tvMovieReleaseDate;

    private static final String API_KEY = BuildConfig.MY_MOVIE_DB_API_KEY;
    private String sMovieUserRating;
    private List<Video> mVideoList = new ArrayList<>();
    private List<Review> mReviewList = new ArrayList<>();
    int movie_id;
    boolean isFavourite;
    boolean favouriteStatus;

    int movieExists;

    // RecyclerView
    private RecyclerView mRecyclerView;
    private ReviewListAdapter mReviewListAdapter;
    private VideoListAdapter mVideoListAdapter;
    // Image Strings
    String backdropImageUrl, moviePosterImageUrl;
    Uri backdropImageUri, moviePosterImageUri;

    @OnClick(R.id.favourite_btn)
    void markMovieAsFavourite() {
        DatabaseAsync databaseAsync = new DatabaseAsync();
        databaseAsync.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_item);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        movie_id = intent.getIntExtra("id", 0);

        initiateVideoListRecyclerView();
        initiateReviewListRecyclerView();
        loadMovieTrailers();
        loadReviews();


        backdropImageUrl = intent.getStringExtra("backdrop");
        backdropImageUri = NetworkUtils.getTmdbBackdropImage(backdropImageUrl);

        moviePosterImageUrl = intent.getStringExtra("posterImage");
        moviePosterImageUri = NetworkUtils.getTmdbPosterImage(moviePosterImageUrl);


        Picasso.with(this)
                .load(backdropImageUri)
                .placeholder(R.drawable.no_image_error)
                .error(R.drawable.no_image_error)
                .into(mMovieBackdropImage);

        Picasso.with(this)
                .load(moviePosterImageUri)
                .placeholder(R.color.colorPrimary)
                .into(mMoviePosterImage);

        tvMovieTitle.setText(intent.getStringExtra("title"));
        tvMovieOverview.setText(intent.getStringExtra("description"));
        // For converting a string into a rating...
        // https://stackoverflow.com/questions/16529640/how-to-set-value-to-rating-bar-with-string
        // Aso I wanted a RatingBar but having 10 stars made it hard to tell quickly what the
        // rating was so I divided the score by 2 and made it out of 5 stars
        sMovieUserRating = intent.getStringExtra("userRating");
        float rating = Float.parseFloat(sMovieUserRating);
        rating = (rating / 2);
        mUserRating.setRating(rating);
        tvMovieReleaseDate.setText(getString(R.string.released) + intent.getStringExtra("releaseDate"));


        // build Movie object for inserting into database
        currentMovie.setId(movie_id);
        currentMovie.setBackdrop(backdropImageUrl);
        currentMovie.setPoster(moviePosterImageUrl);
        currentMovie.setTitle(intent.getStringExtra("title"));
        currentMovie.setDescription(intent.getStringExtra("description"));
        currentMovie.setUserRating(intent.getStringExtra("userRating"));
        currentMovie.setReleaseDate(intent.getStringExtra("releaseDate"));

        CheckFavouritesStatus checkFavouritesStatus = new CheckFavouritesStatus();
        checkFavouritesStatus.execute();

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

    public void toggleMovieFavourite() {

//        https://android.jlelse.eu/room-store-your-data-c6d49b4d53a3
        int movieExists;
        movieDatabase = Room.databaseBuilder(getApplicationContext(), MovieDatabase.class, "movieDatabase").build();
        movieExists = movieDatabase.movieDao().getSingleMovie(currentMovie.getId());
        if (movieExists > 0) {
            currentMovie.setFavourite(false);
            mFavourite_btn.setImageResource(android.R.drawable.btn_star_big_off);
            movieDatabase.movieDao().deleteSingleMovie(currentMovie);
            Log.d("isFavouriteShdBeOff: ", String.valueOf(isFavourite));
        } else {
            currentMovie.setFavourite(true);
            mFavourite_btn.setImageResource(android.R.drawable.btn_star_big_on);
            movieDatabase.movieDao().insertSingleMovie(currentMovie);
            Log.d("isFavouriteShdBeOn: ", String.valueOf(isFavourite));
        }
    }

    private class DatabaseAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            toggleMovieFavourite();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(), "Choice updated", Toast.LENGTH_LONG).show();
        }
    }

    private class CheckFavouritesStatus extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... aVoid) {

            MovieDatabase movieDatabase;
            movieDatabase = Room.databaseBuilder(getApplicationContext(), MovieDatabase.class, "movieDatabase").build();
            movieExists = movieDatabase.movieDao().getSingleMovie(currentMovie.getId());

            return null;
        }

        protected void onPostExecute(Void aVoid) {
            if (movieExists > 0) {
                favouriteStatus = true;
            } else {
                favouriteStatus = false;
            }

            if (favouriteStatus) {
                mFavourite_btn.setImageResource(android.R.drawable.btn_star_big_on);
            } else {
                mFavourite_btn.setImageResource(android.R.drawable.btn_star_big_off);
            }
        }
    }

}
