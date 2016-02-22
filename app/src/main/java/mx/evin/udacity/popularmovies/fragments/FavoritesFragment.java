package mx.evin.udacity.popularmovies.fragments;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.evin.udacity.popularmovies.FavoritesActivity;
import mx.evin.udacity.popularmovies.R;
import mx.evin.udacity.popularmovies.adapters.FavoritesAdapter;
import mx.evin.udacity.popularmovies.entities.Result;
import mx.evin.udacity.popularmovies.providers.FavoritesProvider;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {

    private static final String TAG = "FavoritesFragmentTAG_";
    private Cursor mCursor;
    private ActivityCallback mCallback;

    public FavoritesFragment() {

    }


    public interface ActivityCallback {
        public void onFinishLoading(Result result);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (ActivityCallback) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        retrieveFavsData();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.favsRecycler);
        recyclerView.setAdapter(new FavoritesAdapter((FavoritesActivity) getActivity(), mCursor));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void retrieveFavsData() {
        Uri movies = FavoritesProvider.PROVIDER_URI;
        mCursor = getActivity().getContentResolver().query(movies, null, null, null, null);
        if (mCursor != null && mCursor.getCount() > 0){
            mCursor.moveToFirst();
            mCallback.onFinishLoading(Result.buildResult(mCursor));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCursor.close();
    }
}
