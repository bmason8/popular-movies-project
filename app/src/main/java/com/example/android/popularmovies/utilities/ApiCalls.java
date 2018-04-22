package com.example.android.popularmovies.utilities;

import android.support.v7.app.AppCompatActivity;

public class ApiCalls extends AppCompatActivity {

//    private static final String API_KEY = BuildConfig.MY_MOVIE_DB_API_KEY;
//    private List<Video> mVideo;
//
//    Intent intent = getIntent();
//
//    int movie_id = intent.getIntExtra("id", 0);
//
//    public void fetchVideos(){
//
//        mVideo = new ArrayList<>();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(ApiInterface.MOVIE_DB_BASE_MOVIE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
//        Call<VideoListResponse> call = apiInterface.getMovieVideos(movie_id, API_KEY);
//        call.enqueue(new Callback<VideoListResponse>() {
//            @Override
//            public void onResponse(Call<VideoListResponse> call, Response<VideoListResponse> response) {
//                VideoListResponse result = response.body();
//                mVideo = result.getResults();
//            }
//
//            @Override
//            public void onFailure(Call<VideoListResponse> call, Throwable t) {
//                Toast.makeText(ApiCalls.this, t.getMessage(), Toast.LENGTH_LONG).show();
//                Log.d("Fail: ", t.getMessage());
//            }
//        });
//    }


}
