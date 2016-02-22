package mx.evin.udacity.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import mx.evin.udacity.popularmovies.FavoritesActivity;
import mx.evin.udacity.popularmovies.R;
import mx.evin.udacity.popularmovies.database.MoviesContract.FavoriteEntry;
import mx.evin.udacity.popularmovies.entities.Result;
import mx.evin.udacity.popularmovies.utils.Constants;

/**
 * Created by evin on 2/22/16.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private static final String TAG = "FavoritesAdapterTAG_";
    private FavoritesActivity mFavoritesActivity;
    private CursorAdapter mCursorAdapter;

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
                    mFavoritesActivity.refreshDetails(result);
                }
            });

        }
    }

    public FavoritesAdapter(FavoritesActivity FavoritesActivity, Cursor cursor) {
        mFavoritesActivity = FavoritesActivity;
        mCursorAdapter = new CursorAdapter(FavoritesActivity.getApplicationContext(), cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                return inflater.inflate(R.layout.recycler_item_fav, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {

                TextView txtTitle = (TextView) view.findViewById(R.id.rvItemTitle);
                txtTitle.setText(cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_TITLE)));

                TextView txtDescription = (TextView) view.findViewById(R.id.rvItemShortDescription);
                txtDescription.setText(cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_SYNOPSIS)));

                TextView txtPopularity = (TextView) view.findViewById(R.id.rvItemPopularity);
                Float popularityFloat = Float.valueOf(cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_POPULARITY)));
                txtPopularity.setText(String.format("%.3f", popularityFloat));

                TextView txtRating = (TextView) view.findViewById(R.id.rvItemRating);
                Float ratingFloat = Float.valueOf(cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_RATING)));
                txtRating.setText(String.format("%.2f", ratingFloat));

                String base_url = Constants.BASE_IMG_URL;
                final ImageView imgPath = (ImageView) view.findViewById(R.id.rvItemPath);
                Picasso.with(mFavoritesActivity)
                        .load(base_url + cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_BACKDROP_PATH)))
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
            }
        };
    }

    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mCursorAdapter.newView(mFavoritesActivity.getApplicationContext(), mCursorAdapter.getCursor(), parent);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.ViewHolder viewHolder, int position) {
        Cursor cursor = mCursorAdapter.getCursor();

        cursor.moveToPosition(position);
        viewHolder.result = Result.buildResult(cursor);
        mCursorAdapter.bindView(viewHolder.itemView, mFavoritesActivity.getApplicationContext(), cursor);
    }

    @Override
    public int getItemCount() {
        return mCursorAdapter.getCount();
    }
}