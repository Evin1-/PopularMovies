package mx.evin.udacity.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mx.evin.udacity.popularmovies.R;
import mx.evin.udacity.popularmovies.entities.ReviewResult;

/**
 * Created by evin on 2/22/16.
 */
public class ReviewsAdapter extends  RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private final ArrayList<ReviewResult> mReviews;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.rvVideoTxt);
        }
    }

    public ReviewsAdapter(Context context, ArrayList<ReviewResult> reviews) {
        mReviews = reviews;
        mContext = context;
    }

    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View termView = inflater.inflate(R.layout.recycler_item_review, parent, false);

        return new ViewHolder(termView);
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.ViewHolder holder, int position) {
        ReviewResult reviewResult = mReviews.get(position);
        holder.textView.setText(reviewResult.getContent());
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }
}
