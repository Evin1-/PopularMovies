package mx.evin.udacity.popularmovies;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.ButterKnife;
import mx.evin.udacity.popularmovies.entities.Result;
import mx.evin.udacity.popularmovies.fragments.MainFragment;
import mx.evin.udacity.popularmovies.tasks.RetrieveMoviesTask;
import mx.evin.udacity.popularmovies.utils.Constants;
import mx.evin.udacity.popularmovies.utils.NetworkMagic;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = Constants.TAG_MAIN;

    private MainFragment mMainFragment;

    private ActionBar mActionBar;

    private String mOrderType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: 2/11/16 Change lowercase ids

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mOrderType = "popularity";
        mMainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.mainFragment);
        mActionBar = getSupportActionBar();

        mMainFragment.setRetainInstance(true);

        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.RESULTS_KEY)) {
            if (savedInstanceState.containsKey(Constants.ORDER_TYPE_KEY)) {
                mOrderType = savedInstanceState.getString(Constants.ORDER_TYPE_KEY);
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
        if (!NetworkMagic.isNetworkAvailable(this)) {
            Toast.makeText(MainActivity.this, R.string.internetNotAvailableMessage, Toast.LENGTH_SHORT).show();
            return super.onOptionsItemSelected(item);
        }

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
        outState.putString(Constants.ORDER_TYPE_KEY, mOrderType);

        super.onSaveInstanceState(outState);
    }

    private void toggleOrderType(MenuItem item) {
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
        if (NetworkMagic.isNetworkAvailable(this)){
            new RetrieveMoviesTask(this).execute(arg);
        }
    }

    public void setResults(ArrayList<Result> results) {
        if (mMainFragment != null){
            mMainFragment.refreshRecycler(results);
        }
    }
}
