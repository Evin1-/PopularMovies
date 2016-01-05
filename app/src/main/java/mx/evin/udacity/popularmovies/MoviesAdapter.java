package mx.evin.udacity.popularmovies;

/**
 * Created by evin on 1/5/16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mx.evin.udacity.popularmovies.entities.Result;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private static final String TAG = Constants.TAG_ADAPTER;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public TextView txtPath;
        public TextView txtPopularity;
        public TextView txtRating;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.rvItemTitle);
            txtPath = (TextView) itemView.findViewById(R.id.rvItemPath);
            txtPopularity = (TextView) itemView.findViewById(R.id.rvItemPopularity);
            txtRating = (TextView) itemView.findViewById(R.id.rvItemRating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Item clicked " + v.toString());
                }
            });

        }
    }

    private List<Result> mResults;
    Context context;

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

        TextView txtTitle = viewHolder.txtRating;
        txtTitle.setText(String.format("%.3f", result.getVoteAverage()));

        TextView txtPath = viewHolder.txtTitle;
        txtPath.setText(result.getTitle());

        TextView txtPopularity = viewHolder.txtPath;
        txtPopularity.setText(result.getPosterPath());

        TextView txtRating = viewHolder.txtPopularity;
        txtRating.setText(String.format("%.2f", result.getPopularity()));
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }
}