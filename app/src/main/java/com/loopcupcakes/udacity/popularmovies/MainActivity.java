package com.loopcupcakes.udacity.popularmovies;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.loopcupcakes.udacity.popularmovies.entities.Result;
import com.loopcupcakes.udacity.popularmovies.fragments.MainFragment;
import com.loopcupcakes.udacity.popularmovies.fragments.PlaceholderFragment;
import com.loopcupcakes.udacity.popularmovies.providers.FavoritesProvider;
import com.loopcupcakes.udacity.popularmovies.tasks.RetrieveMoviesTask;
import com.loopcupcakes.udacity.popularmovies.utils.Constants;
import com.loopcupcakes.udacity.popularmovies.utils.NetworkMagic;
import com.loopcupcakes.udacity.popularmovies.utils.SnackbarMagic;

public class MainActivity extends AppCompatActivity implements MainFragment.ActivityCallback {
    private static final String TAG = "MainActivityTAG_";

    private MainFragment mMainFragment;
    private PlaceholderFragment mPlaceholderFragment;

    private ActionBar mActionBar;
    private Result mResult;

    private String mOrderType;

    @Bind(R.id.mainFrame)
    ViewGroup mMainFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: 2/11/16 Add ProgressBar
        // TODO: 2/22/16 Implement SwipeRefreshLayout
        // TODO: 2/22/16 Add scrolling behavior
        // TODO: 2/22/16 Add support to RecyclerView inside ScrollView
        // TODO: 2/23/16 Add navigationDrawer

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mOrderType = "popularity";
        mActionBar = getSupportActionBar();

        mMainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.mainFragment);
        mPlaceholderFragment = (PlaceholderFragment) getSupportFragmentManager().findFragmentById(R.id.placeholderFragment);

        mMainFragment.setRetainInstance(true);

        if (savedInstanceState != null) {
            mOrderType = savedInstanceState.getString(Constants.ORDER_TYPE_KEY, "popularity");
            if (savedInstanceState.containsKey(Constants.RESULT_TEMP_KEY)) {
                mResult = savedInstanceState.getParcelable(Constants.RESULT_TEMP_KEY);
                refreshPlaceholderFragment(mResult);
            }
        } else {
            queryMovieAPI(mOrderType);
        }

        refreshActionBar();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!mOrderType.equals("popularity")) {
            menu.findItem(R.id.menu_toggle).setTitle(R.string.menuTogglePopularity);
        } else {
            menu.findItem(R.id.menu_toggle).setTitle(R.string.menuToggleRating);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_toggle:
                toggleOrderType(item);
                return true;
            case R.id.menu_favs:
                if (checkHasFavorites()) {
                    Intent intent = new Intent(this, FavoritesActivity.class);
                    startActivity(intent);
                } else {
                    SnackbarMagic.showSnackbar(mMainFrame, R.string.noFavoritesYet);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean checkHasFavorites() {
        Uri uri = Uri.withAppendedPath(FavoritesProvider.PROVIDER_URI, "count");
        boolean hasFavorites;

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            hasFavorites = cursor.getInt(0) > 0;
            cursor.close();
        }else {
            return false;
        }

        return hasFavorites;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mOrderType != null) {
            outState.putString(Constants.ORDER_TYPE_KEY, mOrderType);
        }
        if (mResult != null) {
            outState.putParcelable(Constants.RESULT_TEMP_KEY, mResult);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onEmptyResults() {
        queryMovieAPI(mOrderType);
    }

    private void toggleOrderType(MenuItem item) {
        if (!NetworkMagic.isNetworkAvailable(getApplicationContext())) {
            SnackbarMagic.showSnackbar(mMainFrame, R.string.internetNotAvailableMessage);
            return;
        }

        if (item.getTitle().equals(getString(R.string.menuTogglePopularity))) {
            item.setTitle(getString(R.string.menuToggleRating));
            mOrderType = "popularity";
        } else {
            item.setTitle(getString(R.string.menuTogglePopularity));
            mOrderType = "vote_average";
        }

        queryMovieAPI(mOrderType);
        refreshActionBar();
    }

    private void refreshActionBar() {
        if (mActionBar != null) {
            mActionBar.setSubtitle(getString(R.string.subtitleOrderedPrefix) + mOrderType);
            mActionBar.invalidateOptionsMenu();
        }
    }

    public void queryMovieAPI(String arg) {
        if (NetworkMagic.isNetworkAvailable(getApplicationContext())) {
            arg = (arg == null) ? "popularity" : arg;
            new RetrieveMoviesTask(this).execute(arg);
        } else {
            SnackbarMagic.showSnackbar(mMainFrame, R.string.internetNotAvailableMessage);
        }
    }

    public void setResults(List<Result> results) {
        if (mMainFragment != null) {
            mMainFragment.refreshRecycler(results);
        }
        if (results != null && results.size() > 0) {
            if (isTabletLayout()) {
                refreshDetails(results.get(0));
            } else {
                mResult = results.get(0);
            }
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
}