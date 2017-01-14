package com.loopcupcakes.udacity.popularmovies.adapters;

/**
 * Created by evin on 1/5/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopcupcakes.udacity.popularmovies.MainActivity;
import com.loopcupcakes.udacity.popularmovies.R;
import com.loopcupcakes.udacity.popularmovies.entities.Result;
import com.loopcupcakes.udacity.popularmovies.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private static final String TAG = "MoviesAdapterTAG_";
    private final List<Result> mResults;
    private MainActivity mMainActivity;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imgPath;
        public final TextView txtTitle;
        public final TextView txtPopularity;
        public final TextView txtRating;
        public final TextView txtShortDescription;
        public Result result;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.rvItemTitle);
            imgPath = (ImageView) itemView.findViewById(R.id.rvItemPath);
            txtPopularity = (TextView) itemView.findViewById(R.id.rvItemPopularity);
            txtRating = (TextView) itemView.findViewById(R.id.rvItemRating);
            txtShortDescription = (TextView) itemView.findViewById(R.id.rvItemShortDescription);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMainActivity.refreshDetails(result);
                }
            });

        }
    }

    public MoviesAdapter(MainActivity mainActivity, List<Result> results) {
        mMainActivity = mainActivity;
        mResults = results;
    }

    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View termView = inflater.inflate(R.layout.recycler_item, parent, false);

        return new ViewHolder(termView);
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.ViewHolder viewHolder, int position) {
        Result result = mResults.get(position);
        String base_url = Constants.BASE_IMG_URL;

        TextView txtTitle = viewHolder.txtTitle;
        txtTitle.setText(result.getTitle());

        TextView txtDescription = viewHolder.txtShortDescription;
        txtDescription.setText(result.getOverview());

        TextView txtPopularity = viewHolder.txtPopularity;
        txtPopularity.setText(String.format("%.3f", result.getPopularity()));

        TextView txtRating = viewHolder.txtRating;
        txtRating.setText(String.format("%.2f", result.getVoteAverage()));

        final ImageView imgPath = viewHolder.imgPath;
        Picasso.with(mMainActivity.getApplicationContext())
                .load(base_url + result.getBackdropPath())
                .placeholder(R.drawable.large_placeholder)
                .into(imgPath);

        viewHolder.result = result;

    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }
}