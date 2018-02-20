package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

// Helpful Resources...
// https://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/
// Also used the lesson from the course on this topic: S03.02 RecyclerViewClickHandling

public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MovieGridAdapterViewHolder> {
    private Context mContext;
    private List<Movie> mMovieList;
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
        ImageView moviePosterImageView;

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
        int layoutIdForListItem = R.layout.movie_posters;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        final View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieGridAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieGridAdapterViewHolder holder, int position) {
        Movie movie = mMovieList.get(position);
        // load image from web using Picasso
        Picasso.with(mContext)
                .load(movie.getPoster())
                .placeholder(R.color.colorAccent)
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
