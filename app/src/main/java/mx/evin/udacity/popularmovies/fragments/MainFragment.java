package mx.evin.udacity.popularmovies.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import mx.evin.udacity.popularmovies.R;
import mx.evin.udacity.popularmovies.adapters.MoviesAdapter;
import mx.evin.udacity.popularmovies.decorators.SpacesItemDecoration;
import mx.evin.udacity.popularmovies.entities.Result;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    @Bind(R.id.a_main_recycler)
    RecyclerView mRecyclerView;

    private MoviesAdapter mAdapter;
    private ArrayList<Result> mResults;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mResults = new ArrayList<>();
        initializeRecycler();
    }

    private void initializeRecycler() {
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(10));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter = new MoviesAdapter(mResults);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void refreshRecycler(ArrayList<Result> results) {
        if (results != null && results.size() > 0 && mResults != null && mAdapter != null) {
            mResults.clear();
            mResults.addAll(results);
            mAdapter.notifyDataSetChanged();
        }
    }
}
