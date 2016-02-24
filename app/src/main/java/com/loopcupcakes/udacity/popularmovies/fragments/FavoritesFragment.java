package com.loopcupcakes.udacity.popularmovies.fragments;


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

import butterknife.Bind;
import butterknife.ButterKnife;
import com.loopcupcakes.udacity.popularmovies.FavoritesActivity;
import com.loopcupcakes.udacity.popularmovies.R;
import com.loopcupcakes.udacity.popularmovies.adapters.FavoritesAdapter;
import com.loopcupcakes.udacity.popularmovies.decorators.FavSpacesItemDecoration;
import com.loopcupcakes.udacity.popularmovies.entities.Result;
import com.loopcupcakes.udacity.popularmovies.providers.FavoritesProvider;
import com.loopcupcakes.udacity.popularmovies.utils.SnackbarMagic;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {

    private static final String TAG = "FavoritesFragmentTAG_";
    private Cursor mCursor;
    private ActivityCallback mCallback;

    private FavoritesAdapter mAdapter;
    @Bind(R.id.favsRecycler)
    RecyclerView mRecyclerView;

    public FavoritesFragment() {

    }


    public interface ActivityCallback {
        void onFinishLoading(Result result);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (ActivityCallback) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshData();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new FavSpacesItemDecoration(10));
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    private void retrieveFavData() {
        Uri movies = FavoritesProvider.PROVIDER_URI;
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = getActivity().getContentResolver().query(movies, null, null, null, null);
        if (mCursor != null && mCursor.getCount() > 0) {
            mCursor.moveToFirst();
            mCallback.onFinishLoading(Result.buildResult(mCursor));
        } else {
            View view = getView();
            if (view != null) {
                SnackbarMagic.showSnackbar(view, R.string.noFavoritesYet);
            }
        }
    }

    public void refreshData() {
        // TODO: 2/22/16 Refresh content with a Loader or SyncAdapter
        retrieveFavData();
        mAdapter = new FavoritesAdapter((FavoritesActivity) getActivity(), mCursor);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCursor.close();
    }
}
