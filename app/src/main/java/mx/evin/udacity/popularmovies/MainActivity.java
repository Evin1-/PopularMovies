package mx.evin.udacity.popularmovies;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import mx.evin.udacity.popularmovies.entities.Result;

public class MainActivity extends AppCompatActivity {
    // TODO: 2/10/16 Add placeholder and error Picasso

    private static final String TAG = Constants.TAG_MAIN;
    private ArrayList<Result> mResults;

    private MoviesAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ActionBar mActionBar;

    private String mOrderType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mResults = new ArrayList<>();
        mOrderType = "popularity";

        mRecyclerView = (RecyclerView) findViewById(R.id.rvMainResults);
        mActionBar = getSupportActionBar();
        initializeRecycler();

        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.mResultsKey)) {
            setResults(savedInstanceState.<Result>getParcelableArrayList(Constants.mResultsKey));
            if (savedInstanceState.containsKey(Constants.mOrderTypeKey)) {
                mOrderType = savedInstanceState.getString(Constants.mOrderTypeKey);
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
        if (!NetworkUtils.isNetworkAvailable(this)) {
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
        outState.putParcelableArrayList(Constants.mResultsKey, mResults);
        outState.putString(Constants.mOrderTypeKey, mOrderType);

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

    private void initializeRecycler() {
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(10));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MoviesAdapter(mResults);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void queryMovieAPI(String arg) {
        new RetrieveMovies(this).execute(arg);
    }

    public void setResults(ArrayList<Result> results) {
        if (results != null && results.size() > 0 && mResults != null && mAdapter != null) {
            mResults.clear();
            mResults.addAll(results);
            mAdapter.notifyDataSetChanged();
        }
    }
}
