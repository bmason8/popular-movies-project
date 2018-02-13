package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MovieGridAdapterViewHolder> {
    private Context mContext;
    private List<Movie> mMovieList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public MovieGridAdapter(Context context) {
        this.mContext = context;
        this.mMovieList = new ArrayList<>();
    }


    public class MovieGridAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView moviePosterImageView;

        public MovieGridAdapterViewHolder(View view) {
            super(view);
            moviePosterImageView = view.findViewById(R.id.movie_poster);
            // catch the click on the view in our adapter and pass it over the interface to our activity
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null) {
                        int position = getAdapterPosition();
                        // make sure the position is still valid
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
//            int adapterPosition = getAdapterPosition();
            Movie movie = mMovieList.get(getAdapterPosition());
            final Intent intent = new Intent(mContext, MovieDetailActivity.class);
            intent.putExtra("posterImage", movie.getPoster());
            intent.putExtra("backdrop", movie.getBackdrop());
            intent.putExtra("title", movie.getTitle());
            intent.putExtra("description", movie.getDescription());
            intent.putExtra("userRating", movie.getUserRating());
            intent.putExtra("releaseDate", movie.getReleaseDate());
            Toast.makeText(mContext, movie.getBackdrop(), Toast.LENGTH_SHORT).show();
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
        return (mMovieList == null) ? 0 : mMovieList.size();
    }

    public void  setmMovieList(List<Movie>movieList) {
        this.mMovieList.clear();
        this.mMovieList.addAll(movieList);
        notifyDataSetChanged();
    }

}
