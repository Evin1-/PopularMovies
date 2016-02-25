package com.loopcupcakes.udacity.popularmovies;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.loopcupcakes.udacity.popularmovies.entities.Result;
import com.loopcupcakes.udacity.popularmovies.fragments.PlaceholderFragment;
import com.loopcupcakes.udacity.popularmovies.utils.SnackbarMagic;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivityTAG_";
    public static final String MOVIE_PARCELABLE_TAG = "movie_parcelable_tag";

    private PlaceholderFragment mPlaceholderFragment;

    @Bind(R.id.detailsFrame)
    ViewGroup mDetailsFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (isTablet()) {
            finish();
        }

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }

        Bundle b = getIntent().getExtras();
        Result movie = b.getParcelable(MOVIE_PARCELABLE_TAG);

        mPlaceholderFragment = (PlaceholderFragment) getSupportFragmentManager().findFragmentById(R.id.placeholderFragment);

        if (movie == null) {
            SnackbarMagic.showSnackbar(mDetailsFrame, R.string.retrieveMovieFailedMessage);
            this.finish();
        } else {
            mPlaceholderFragment.refreshContent(movie);
        }
    }

    private boolean isTablet() {
        return getWidthInDp() > 600;
    }

    private float getWidthInDp() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return displayMetrics.widthPixels / displayMetrics.density;
    }

}
