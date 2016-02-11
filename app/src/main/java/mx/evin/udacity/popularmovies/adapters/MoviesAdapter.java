package mx.evin.udacity.popularmovies.adapters;

/**
 * Created by evin on 1/5/16.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import mx.evin.udacity.popularmovies.DetailsActivity;
import mx.evin.udacity.popularmovies.R;
import mx.evin.udacity.popularmovies.entities.Result;
import mx.evin.udacity.popularmovies.utils.Constants;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private static final String TAG = Constants.TAG_ADAPTER;

    public static class ViewHolder extends RecyclerView.ViewHolder {
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
                    Intent intent = new Intent(v.getContext(), DetailsActivity.class);
                    Bundle b = new Bundle();

                    b.putParcelable("movie", result);
                    intent.putExtras(b);
                    v.getContext().startActivity(intent);
                }
            });

        }
    }

    private final List<Result> mResults;
    private Context context;

    public MoviesAdapter(List<Result> results) {
        mResults = results;
    }

    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View termView = inflater.inflate(R.layout.recycler_item, parent, false);

        return new ViewHolder(termView);
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.ViewHolder viewHolder, int position) {
        Result result = mResults.get(position);
        String base_url = Constants.BASE_IMG_URL;

        TextView txtTitle = viewHolder.txtRating;
        txtTitle.setText(String.format("%.3f", result.getVoteAverage()));

        TextView txtPath = viewHolder.txtTitle;
        txtPath.setText(result.getTitle());

        TextView txtDescription = viewHolder.txtShortDescription;
        txtDescription.setText(result.getOverview());

        final ImageView imgPath = viewHolder.imgPath;
        Picasso.with(context)
                .load(base_url + result.getBackdropPath())
                .placeholder(R.drawable.large_placeholder)
                .into(imgPath, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        imgPath.setVisibility(View.GONE);
                    }
                });

        TextView txtRating = viewHolder.txtPopularity;
        txtRating.setText(String.format("%.2f", result.getPopularity()));

        viewHolder.result = result;

    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }
}