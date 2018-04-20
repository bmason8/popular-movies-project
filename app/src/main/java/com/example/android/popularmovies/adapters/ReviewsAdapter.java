package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Reviews;

import java.util.List;

public class ReviewsAdapter  extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder>{

    private List<Reviews> reviewsList;
    private Context context;

    public ReviewsAdapter(List<Reviews> reviewsList, Context context) {
        this.reviewsList = reviewsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        int layoutIdForListItem = R.layout.reviews_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        final  View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Reviews reviews = reviewsList.get(position);

        holder.tvAuthor.setText(reviews.getAuthor());
        holder.tvContent.setText(reviews.getContent());
    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvAuthor;
        public TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);

            tvAuthor = itemView.findViewById(R.id.tv_author_list_item);
            tvContent= itemView.findViewById(R.id.tv_content_list_item);
        }
    }

    public void setmReviewsList(List<Reviews>reviewsList){
        this.reviewsList.clear();
        this.reviewsList.addAll(reviewsList);
        notifyDataSetChanged();
    }


}








//    public ReviewsAdapter(Context context) {
//        this.mContext = context;
//        this.mReviewsList = new ArrayList<>();
//    }
//
//
//    public class ReviewsAdapterViewHolder extends RecyclerView.Adapter<ReviewsAdapter.ReviewsAdapterViewHolder> {
//
//
//        public ReviewsAdapter {
//            super(view);
//            moviePosterImageView = view.findViewById(R.id.movie_poster);
//            // catch the click on the view in our adapter and pass it over the interface to our activity
//            view.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            int position = getAdapterPosition();
//            mListener.onItemClick(position);
//        }
//    }
//
//
//    @Override
//    public ReviewsAdapter.ReviewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        final Context context = viewGroup.getContext();
//        int layoutIdForListItem = R.layout.movie_poster_item;
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        final View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
//        return new ReviewsAdapter.ReviewsAdapterViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ReviewsAdapter.ReviewsAdapterViewHolder holder, int position) {
//        Reviews reviews = mReviewsList.get(position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mReviewsList.size();
//    }
//
//    public void setmReviewsList(List<Reviews> reviewsList) {
//        this.mReviewsList.clear();
//        this.mReviewsList.addAll(reviewsList);
//        notifyDataSetChanged();
//    }
