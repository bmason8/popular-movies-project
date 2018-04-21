package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Review;

import java.util.List;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder>{

    private final Context mContext;
    private final List<Review> mReviewList;


    public ReviewListAdapter( Context context, List<Review> reviewList) {
        this.mContext = context;
        this.mReviewList = reviewList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.reviews_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.tvAuthor.setText(mReviewList.get(position).getAuthor());
        viewHolder.tvContent.setText(mReviewList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvAuthor;
        private TextView tvContent;

        ViewHolder(View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tv_author_list_item);
            tvContent= itemView.findViewById(R.id.tv_content_list_item);
        }
    }

    public void setmReviewList(List<Review> reviewList){
        if (reviewList == null) {
            return;
        }
        mReviewList.clear();
        mReviewList.addAll(reviewList);
        notifyDataSetChanged();
    }


}








//    public ReviewListAdapter(Context context) {
//        this.mContext = context;
//        this.mReviewsList = new ArrayList<>();
//    }
//
//
//    public class ReviewsAdapterViewHolder extends RecyclerView.Adapter<ReviewListAdapter.ReviewsAdapterViewHolder> {
//
//
//        public ReviewListAdapter {
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
//    public ReviewListAdapter.ReviewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        final Context context = viewGroup.getContext();
//        int layoutIdForListItem = R.layout.movie_poster_item;
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        final View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
//        return new ReviewListAdapter.ReviewsAdapterViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ReviewListAdapter.ReviewsAdapterViewHolder holder, int position) {
//        Review reviews = mReviewsList.get(position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mReviewsList.size();
//    }
//
//    public void setmReviewsList(List<Review> mReviewList) {
//        this.mReviewsList.clear();
//        this.mReviewsList.addAll(mReviewList);
//        notifyDataSetChanged();
//    }
