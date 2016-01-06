package mx.evin.udacity.popularmovies;

/**
 * Created by evin on 1/5/16.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mx.evin.udacity.popularmovies.entities.Result;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private static final String TAG = Constants.TAG_ADAPTER;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgPath;
        public TextView txtTitle;
        public TextView txtPopularity;
        public TextView txtRating;
        public String path;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.rvItemTitle);
            imgPath = (ImageView) itemView.findViewById(R.id.rvItemPath);
            txtPopularity = (TextView) itemView.findViewById(R.id.rvItemPopularity);
            txtRating = (TextView) itemView.findViewById(R.id.rvItemRating);
            path = "1";

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Item clicked " + v.toString());
                    Log.d(TAG, "Item clicked " + v.toString());
                    Log.d(TAG, path);
//                    String url = ((TextView) v.findViewById(R.id.rvItemPath)).getText().toString();
                    Intent intent = new Intent(v.getContext(), DetailsActivity.class);
                    Bundle b = new Bundle();
//                    b.putInt("key", 1); //Your id
                    b.putString("key", path);
                    intent.putExtras(b); //Put your id to your next Intent
                    v.getContext().startActivity(intent);
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
        String base_url = "http://image.tmdb.org/t/p/w500/";

        TextView txtTitle = viewHolder.txtRating;
        txtTitle.setText(String.format("%.3f", result.getVoteAverage()));

        TextView txtPath = viewHolder.txtTitle;
        txtPath.setText(result.getTitle());

        ImageView imgPath = viewHolder.imgPath;
        Picasso.with(context)
                .load(base_url + result.getBackdropPath())
                .into(imgPath);

        TextView txtRating = viewHolder.txtPopularity;
        txtRating.setText(String.format("%.2f", result.getPopularity()));

        viewHolder.path = base_url + result.getPosterPath();

    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }
}