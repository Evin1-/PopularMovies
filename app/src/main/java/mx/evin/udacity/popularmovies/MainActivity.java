package mx.evin.udacity.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import mx.evin.udacity.popularmovies.entities.Result;

public class MainActivity extends AppCompatActivity {
    // TODO: 2/10/16 Set popularity|rating as variable
    // TODO: 2/10/16 Add placeholder and error Picasso

    private static final String TAG = Constants.TAG_MAIN;
    private ArrayList<Result> mResults;

    private MoviesAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mResults = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.rvMainResults);
        initializeRecycler();

        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.mResults_key)) {
            setResults(savedInstanceState.<Result>getParcelableArrayList(Constants.mResults_key));
            Log.d(TAG, "onCreate: savedInstance Not null");
        } else {
            queryMovieAPI("popularity");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.toggle:
                if (item.getTitle().equals(getString(R.string.menuTogglePopularity))) {
                    item.setTitle(getString(R.string.menuToggleRating));
                    queryMovieAPI("popularity");
                    new RetrieveMovies(this).execute("popularity");
                } else {
                    item.setTitle(getString(R.string.menuTogglePopularity));
                    queryMovieAPI("vote_average");
                    new RetrieveMovies(this).execute("vote_average");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: ");
        outState.putParcelableArrayList(Constants.mResults_key, mResults);
        super.onSaveInstanceState(outState);
    }

    private void initializeRecycler() {
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(5));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MoviesAdapter(mResults);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void queryMovieAPI(String arg) {
        new RetrieveMovies(this).execute(arg);
    }

    public void setResults(ArrayList<Result> results) {
        if (results != null && results.size() > 0 && mResults != null && mAdapter != null){
            mResults.clear();
            mResults.addAll(results);
            mAdapter.notifyDataSetChanged();
        }
    }
}
