package mx.evin.udacity.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

        TextView textViewAuthor;
        TextView textViewContent;
        TextView textViewURL;
        String reviewURL;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewAuthor = (TextView) itemView.findViewById(R.id.rvReviewAuthor);
            textViewContent = (TextView) itemView.findViewById(R.id.rvReviewContent);
            textViewURL = (TextView) itemView.findViewById(R.id.rvReviewURL);

            textViewURL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(reviewURL));
                    mContext.startActivity(intent);
                }
            });
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
        final String reviewResultUrl = reviewResult.getUrl();

        TextView textViewAuthor = holder.textViewAuthor;
        textViewAuthor.setText(reviewResult.getAuthor());

        TextView textViewContent = holder.textViewContent;
        textViewContent.setText(reviewResult.getContent());

        TextView textViewURL = holder.textViewURL;
        textViewURL.setText(reviewResultUrl);

        holder.reviewURL = reviewResultUrl;
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }
}
