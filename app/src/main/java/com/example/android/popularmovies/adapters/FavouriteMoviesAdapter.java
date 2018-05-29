package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.MovieDetailActivity;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.MovieContract.MovieDbEntry;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

// ADD A CURSOR TO THIS ADAPTER!!!

public class FavouriteMoviesAdapter extends RecyclerView.Adapter<FavouriteMoviesAdapter.FavouriteMoviesAdapterViewHolder> {

    private final Context mContext;
    private final List<Movie> mMovieList;
    private Cursor mCursor;
    private FavouriteMoviesAdapterOnClickHandler mListener;


    public FavouriteMoviesAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
        this.mMovieList = new ArrayList<>();
    }

    public void FavouriteMoviesAdapterClickListener(FavouriteMoviesAdapterOnClickHandler clickHandler) {
        mListener = clickHandler;
    }

    @Override
    public FavouriteMoviesAdapter.FavouriteMoviesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        final Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_poster_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        final View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new FavouriteMoviesAdapter.FavouriteMoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavouriteMoviesAdapter.FavouriteMoviesAdapterViewHolder holder, int position) {
        if (!mCursor.move(position)) {
            return;
        }
        String posterPathString = mCursor.getString(mCursor.getColumnIndex(MovieDbEntry.COLUMN_POSTER_PATH));


//        Movie movie = mMovieList.get(position);
        Uri posterPath = NetworkUtils.getTmdbPosterImage(posterPathString);
        // load image from web using Picasso
        Picasso.with(mContext)
                .load(posterPath)
                .placeholder(R.color.colorPrimary)
                .error(R.drawable.no_image_poster)
                .into(holder.moviePosterImageView);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (mCursor != null) {
            this.notifyDataSetChanged();
        }
    }

    public void setmMovieList(List<Movie> movieList) {
        this.mMovieList.clear();
        this.mMovieList.addAll(movieList);
        notifyDataSetChanged();
    }

    public interface FavouriteMoviesAdapterOnClickHandler {
        void onItemClick(int position);
    }

    public class FavouriteMoviesAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        final ImageView moviePosterImageView;

        public FavouriteMoviesAdapterViewHolder(View view) {
            super(view);
            moviePosterImageView = view.findViewById(R.id.movie_poster);
            // catch the click on the view in our adapter and pass it over the interface to our activity
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
//            mListener.onItemClick(position);
            Movie movie = mMovieList.get(position);
            Intent intent = new Intent(mContext, MovieDetailActivity.class);
            intent.putExtra("id", movie.getId());
            intent.putExtra("posterImage", movie.getPoster());
            intent.putExtra("backdrop", movie.getBackdrop());
            intent.putExtra("title", movie.getTitle());
            intent.putExtra("description", movie.getDescription());
            intent.putExtra("userRating", movie.getUserRating());
            intent.putExtra("releaseDate", movie.getReleaseDate());
            mContext.startActivity(intent);

        }
    }

}
