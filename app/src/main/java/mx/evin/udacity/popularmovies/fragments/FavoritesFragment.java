package mx.evin.udacity.popularmovies.fragments;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.evin.udacity.popularmovies.R;
import mx.evin.udacity.popularmovies.database.MoviesContract;
import mx.evin.udacity.popularmovies.providers.FavoritesProvider;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {

    private static final String TAG = "FavoritesFragmentTAG_";

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        retrieveFavsData();
    }

    private void retrieveFavsData() {
        Uri movies = FavoritesProvider.PROVIDER_URI;
        Cursor c = getActivity().managedQuery(movies, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                Log.d(TAG, "testContent: " +
                        c.getString(c.getColumnIndex(MoviesContract.FavoriteEntry._ID)) +
                        ", " + c.getString(c.getColumnIndex(MoviesContract.FavoriteEntry.COLUMN_TITLE)) +
                        ", " + c.getString(c.getColumnIndex(MoviesContract.FavoriteEntry.COLUMN_RATING)));
            } while (c.moveToNext());
        }
    }
}
