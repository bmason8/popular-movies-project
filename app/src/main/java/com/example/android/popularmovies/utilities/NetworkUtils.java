package com.example.android.popularmovies.utilities;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    private static final String BASE_YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/";
    private static final String BASE_YOUTUBE_URL = "https://www.youtube.com/";
    private static final String YOUTUBE_VIDEO_WATCH_PATH = "watch";
    private static final String YOUTUBE_VIDEO_ID_PARAM = "v";

    private static URL buildYoutubeUrl(Uri uri) {
            try {
                return new URL(uri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        }

    public static Uri getYouTubeThumbnailImage(String videoId) {
        Uri thumbnailUri = Uri.parse(BASE_YOUTUBE_THUMBNAIL_URL).buildUpon()
                .appendEncodedPath(videoId)
                .appendEncodedPath("mqdefault.jpg")
                .build();
        return thumbnailUri;
    }

    public static URL getYouTubeVideoUrl(String videoId) {
        Uri videoUri = Uri.parse(BASE_YOUTUBE_URL).buildUpon()
                .appendEncodedPath(YOUTUBE_VIDEO_WATCH_PATH)
                .appendQueryParameter(YOUTUBE_VIDEO_ID_PARAM, videoId).build();

        return buildYoutubeUrl(videoUri);
    }

}
