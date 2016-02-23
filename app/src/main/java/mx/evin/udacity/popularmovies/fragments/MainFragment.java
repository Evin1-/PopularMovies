package mx.evin.udacity.popularmovies.fragments;


import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import mx.evin.udacity.popularmovies.MainActivity;
import mx.evin.udacity.popularmovies.R;
import mx.evin.udacity.popularmovies.adapters.MoviesAdapter;
import mx.evin.udacity.popularmovies.decorators.SpacesItemDecoration;
import mx.evin.udacity.popularmovies.entities.Result;
import mx.evin.udacity.popularmovies.receivers.NetworkReceiver;
import mx.evin.udacity.popularmovies.utils.Constants;
import mx.evin.udacity.popularmovies.utils.NetworkMagic;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private static final String TAG = "MainFragmentTAG_";
    @Bind(R.id.mainRecycler)
    RecyclerView mRecyclerView;

    private MoviesAdapter mAdapter;
    private ArrayList<Result> mResults;

    private ActivityCallback mCallback;
    NetworkReceiver mNetworkReceiver;

    public MainFragment() {

    }

    public interface ActivityCallback {
        void onEmptyResults();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (ActivityCallback) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mResults = new ArrayList<>();
        initializeRecycler();
        if (savedInstanceState != null) {
            refreshRecycler(savedInstanceState.<Result>getParcelableArrayList(Constants.RESULTS_KEY));
        }
    }

    private void initializeRecycler() {
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(10));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter = new MoviesAdapter((MainActivity) getActivity(), mResults);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void refreshRecycler(ArrayList<Result> results) {
        if (results != null && results.size() > 0 && mResults != null && mAdapter != null) {
            mResults.clear();
            mResults.addAll(results);
            mAdapter.notifyDataSetChanged();
        } else {
            mCallback.onEmptyResults();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Constants.RESULTS_KEY, mResults);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!NetworkMagic.isNetworkAvailable(getActivity())) {
            mNetworkReceiver = new NetworkReceiver(this);
            IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            getActivity().registerReceiver(mNetworkReceiver, intentFilter);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mNetworkReceiver != null) {
            try {
                getActivity().unregisterReceiver(mNetworkReceiver);
            } catch (Exception e) {
                Log.e(TAG, "onPause: " + e.toString());
            }
            mNetworkReceiver = null;
        }
    }

    public ActivityCallback getActivityCallback() {
        return mCallback;
    }

    public boolean isActuallyEmpty() {
        return mResults == null || mResults.size() < 1;
    }
}
