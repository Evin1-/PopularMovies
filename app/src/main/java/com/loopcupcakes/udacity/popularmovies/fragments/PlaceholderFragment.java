package com.loopcupcakes.udacity.popularmovies.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.loopcupcakes.udacity.popularmovies.R;
import com.loopcupcakes.udacity.popularmovies.entities.Result;
import com.loopcupcakes.udacity.popularmovies.utils.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceholderFragment extends Fragment {

    @Bind(R.id.detailsBackgroundImage)
    ImageView mImageView;

    private Result mResult;
    private DetailsFragment mDetailsFragment;

    public PlaceholderFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_placeholder, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDetailsFragment = (DetailsFragment) getChildFragmentManager().findFragmentById(R.id.detailsFragment);
    }

    public void refreshContent(Result result) {
        mResult = result;

        if (mImageView != null && mResult != null) {
            drawBackgroundImage();
        }
        if (mDetailsFragment != null) {
            mDetailsFragment.refreshDetails(result);
        }
    }

    private void drawBackgroundImage() {
        Picasso.with(getActivity().getApplicationContext())
                .load(Constants.BASE_IMG_URL + mResult.getPosterPath())
                .placeholder(R.drawable.large_placeholder)
                .into(mImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        mImageView.setVisibility(View.INVISIBLE);
                    }
                });
    }
}
