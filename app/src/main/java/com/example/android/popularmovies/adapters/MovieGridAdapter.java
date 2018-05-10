package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

// Helpful Resources...
// https://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/
// Also used the lesson from the course on this topic: S03.02 RecyclerViewClickHandling

public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MovieGridAdapterViewHolder> {

    private final Context mContext;
    private final List<Movie> mMovieList;
    private MovieGridAdapterOnClickHandler mListener;



    public interface MovieGridAdapterOnClickHandler {
        void onItemClick(int position);
    }


    public void MovieGridAdapterClickListener(MovieGridAdapterOnClickHandler clickHandler) {
        mListener = clickHandler;
    }


    public MovieGridAdapter(Context context) {
        this.mContext = context;
        this.mMovieList = new ArrayList<>();
    }


    public class MovieGridAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        final ImageView moviePosterImageView;

        public MovieGridAdapterViewHolder(View view) {
            super(view);
            moviePosterImageView = view.findViewById(R.id.movie_poster);
            // catch the click on the view in our adapter and pass it over the interface to our activity
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
                int position = getAdapterPosition();
                mListener.onItemClick(position);
        }
    }


    @Override
    public MovieGridAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        final Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_poster_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        final View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new MovieGridAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieGridAdapterViewHolder holder, int position) {
        Movie movie = mMovieList.get(position);
        Uri posterPath = NetworkUtils.getTmdbPosterImage(movie.getPoster());
        // load image from web using Picasso
        Picasso.with(mContext)
                .load(posterPath)
                .placeholder(R.color.colorPrimary)
                .error(R.drawable.no_image_poster)
                .into(holder.moviePosterImageView);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public void setmMovieList(List<Movie>movieList) {
        this.mMovieList.clear();
        this.mMovieList.addAll(movieList);
        notifyDataSetChanged();
    }

}