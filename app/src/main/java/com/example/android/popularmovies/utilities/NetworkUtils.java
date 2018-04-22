package com.example.android.popularmovies.utilities;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    // https://www.themoviedb.org/talk/5451ec02c3a3680245005e3c
    // used for figuring how to build a Youtube url from the TMDB

    private static final String BASE_YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/";
    private static final String BASE_YOUTUBE_URL = "https://www.youtube.com/";
    private static final String YOUTUBE_VIDEO_WATCH_PATH = "watch";
    private static final String YOUTUBE_VIDEO_ID_PARAM = "v";

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
                .appendEncodedPath("mqdefault.jpg")
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

}
