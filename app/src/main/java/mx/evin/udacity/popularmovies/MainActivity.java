package mx.evin.udacity.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import mx.evin.udacity.popularmovies.entities.Result;
import mx.evin.udacity.popularmovies.fragments.MainFragment;
import mx.evin.udacity.popularmovies.fragments.PlaceholderFragment;
import mx.evin.udacity.popularmovies.tasks.RetrieveMoviesTask;
import mx.evin.udacity.popularmovies.utils.Constants;
import mx.evin.udacity.popularmovies.utils.NetworkMagic;
import mx.evin.udacity.popularmovies.utils.SnackbarMagic;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = Constants.TAG_MAIN;

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
            menu.findItem(R.id.toggle).setTitle(R.string.menuTogglePopularity);
        } else {
            menu.findItem(R.id.toggle).setTitle(R.string.menuToggleRating);
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
            case R.id.toggle:
                toggleOrderType(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    private void toggleOrderType(MenuItem item) {
        if (!NetworkMagic.isNetworkAvailable(this)) {
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
        if (NetworkMagic.isNetworkAvailable(this)) {
            new RetrieveMoviesTask(this).execute(arg);
        } else {
            SnackbarMagic.showSnackbar(mMainFrame, R.string.internetNotAvailableMessage);
        }
    }

    public void setResults(ArrayList<Result> results) {
        if (mMainFragment != null) {
            mMainFragment.refreshRecycler(results);
        }
        if (isTabletLayout() && results != null && results.size() > 0) {
            refreshDetails(results.get(0));
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

            b.putParcelable("movie", mResult);
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
