package com.example.android.popularmovies.adapters;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Video;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoViewHolder> {
    private Context mContext;
    private List<Video> mVideoList;

    public VideoListAdapter(Context mContext, List<Video> mVideoList) {
        this.mContext = mContext;
        this.mVideoList = mVideoList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.videos_list, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, final int position) {
        final String videoId = mVideoList.get(position).getKey();
        holder.setVideoId(videoId);
        Uri videoThumbnailUri = NetworkUtils.getYouTubeThumbnailImage(videoId);
        Picasso.with(mContext).load(videoThumbnailUri).into(holder.thumbNailImgView);

        holder.nameTv.setText(mVideoList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    public void setData(List<Video> videoList) {
        if (videoList == null) {
            return;
        }
        mVideoList.clear();
        mVideoList.addAll(videoList);
        notifyDataSetChanged();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        static final String YOUTUBE_VND = "vnd.youtube:";
        private ImageView thumbNailImgView;
        private String mVideoId;
        private TextView nameTv;

        VideoViewHolder(View itemView) {
            super(itemView);
            thumbNailImgView = itemView.findViewById(R.id.img_movie_trailer_list_item);
            nameTv = itemView.findViewById(R.id.tv_movie_trailer_list_item);
            itemView.setOnClickListener(this);
        }

        void setVideoId(String movieId) {
            this.mVideoId = movieId;
        }

        // https://stackoverflow.com/questions/9439995/how-to-launch-youtube-application-to-open-a-channel
        // used for figuring out how to launch the video from Youtube

        @Override
        public void onClick(View v) {
            // if the Youtube app isn't installed it'll launch it in a browser
            Intent youtubeAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_VND + mVideoId));
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(NetworkUtils.getYouTubeVideoUrl(mVideoId).toString()));
            try {
                mContext.startActivity(youtubeAppIntent);
            } catch (ActivityNotFoundException ex) {
                mContext.startActivity(browserIntent);
            }
        }
    }
}