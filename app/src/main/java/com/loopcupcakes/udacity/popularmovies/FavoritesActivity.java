package com.loopcupcakes.udacity.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.loopcupcakes.udacity.popularmovies.entities.Result;
import com.loopcupcakes.udacity.popularmovies.fragments.DetailsFragment;
import com.loopcupcakes.udacity.popularmovies.fragments.FavoritesFragment;
import com.loopcupcakes.udacity.popularmovies.fragments.PlaceholderFragment;
import com.loopcupcakes.udacity.popularmovies.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FavoritesActivity extends AppCompatActivity implements FavoritesFragment.ActivityCallback, DetailsFragment.ActivityCallback {

    private static final String TAG = "FavoritesActivityTAG_";
    private PlaceholderFragment mPlaceholderFragment;
    private FavoritesFragment mFavoritesFragment;

    private Result mResult;
    private ActionBar mActionBar;

    @Bind(R.id.mainFavoritesToolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        ButterKnife.bind(this);

        mFavoritesFragment = (FavoritesFragment) getSupportFragmentManager().findFragmentById(R.id.favoritesFragment);
        mPlaceholderFragment = (PlaceholderFragment) getSupportFragmentManager().findFragmentById(R.id.placeholderFragment);

        mFavoritesFragment.setRetainInstance(true);

        setupActionBar();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.RESULT_TEMP_KEY)) {
                mResult = savedInstanceState.getParcelable(Constants.RESULT_TEMP_KEY);
                refreshPlaceholderFragment(mResult);
            }
        } else {
            if (isTabletLayout()) {
                refreshPlaceholderFragment(mResult);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mResult != null) {
            outState.putParcelable(Constants.RESULT_TEMP_KEY, mResult);
        }
        super.onSaveInstanceState(outState);
    }


    private void setupActionBar() {
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setSubtitle(R.string.subtitleFavorites);
        }
    }

    private boolean isTabletLayout() {
        return mPlaceholderFragment != null && mPlaceholderFragment.isAdded();
    }

    public void refreshDetails(Result result) {
        if (result == null) {
            return;
        }

        mResult = result;

        if (isTabletLayout()) {
            refreshPlaceholderFragment(mResult);
        } else {
            Intent intent = new Intent(this, DetailsActivity.class);
            Bundle b = new Bundle();

            b.putParcelable(DetailsActivity.MOVIE_PARCELABLE_TAG, mResult);
            intent.putExtras(b);
            startActivity(intent);
        }
    }

    private void refreshPlaceholderFragment(Result result) {
        if (mPlaceholderFragment != null) {
            mPlaceholderFragment.refreshContent(result);
        }
    }

    @Override
    public void onFinishLoading(Result result) {
        if (mResult == null) {
            mResult = result;
        }
    }

    @Override
    public void onModifiedFavorites() {
        if (mFavoritesFragment != null && mFavoritesFragment.isAdded()) {
            mFavoritesFragment.refreshData();
        }
    }
}
