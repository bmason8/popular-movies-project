package com.example.android.popularmovies.utilities;

import android.net.Uri;
import android.support.design.widget.FloatingActionButton;

import com.example.android.popularmovies.R;

import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;

public class NetworkUtils {

    @BindView(R.id.favourite_btn)
    FloatingActionButton mFavourite_btn;

    // https://www.themoviedb.org/talk/5451ec02c3a3680245005e3c
    // used for figuring how to build a Youtube url from the TMDB

    private static final String BASE_YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/";
    private static final String BASE_YOUTUBE_URL = "https://www.youtube.com/";
    private static final String YOUTUBE_VIDEO_WATCH_PATH = "watch";
    private static final String YOUTUBE_VIDEO_ID_PARAM = "v";

    private static final String TMDB_POSTER_IMAGE_PATH = "http://image.tmdb.org/t/p/w342";
    private static final String TMDB_BACKDROP_IMAGE_PATH = "https://image.tmdb.org/t/p/w500";

    // builds URL from Uri for Youtube links
    private static URL buildYoutubeUrl(Uri uri) {
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // builds Uri for youtube trailer thumbnail
    // https://stackoverflow.com/questions/2068344/how-do-i-get-a-youtube-video-thumbnail-from-the-youtube-api?rq=1
    public static Uri getYouTubeThumbnailImage(String videoId) {
        Uri thumbnailUri = Uri.parse(BASE_YOUTUBE_THUMBNAIL_URL).buildUpon()
                .appendEncodedPath(videoId)
                .appendEncodedPath("hqdefault.jpg")
                .build();
        return thumbnailUri;
    }

    // builds URL for movie trailers from Youtube
    public static URL getYouTubeVideoUrl(String videoId) {
        Uri videoUri = Uri.parse(BASE_YOUTUBE_URL).buildUpon()
                .appendEncodedPath(YOUTUBE_VIDEO_WATCH_PATH)
                .appendQueryParameter(YOUTUBE_VIDEO_ID_PARAM, videoId).build();

        return buildYoutubeUrl(videoUri);
    }

    public static Uri getTmdbBackdropImage(String backdropImagePath) {
        Uri backdropUri = Uri.parse(TMDB_BACKDROP_IMAGE_PATH).buildUpon()
                .appendEncodedPath(backdropImagePath)
                .build();
        return backdropUri;
    }

    public static Uri getTmdbPosterImage(String imagePath) {
        Uri posterUri = Uri.parse(TMDB_POSTER_IMAGE_PATH).buildUpon()
                .appendEncodedPath(imagePath)
                .build();
        return posterUri;
    }
}
