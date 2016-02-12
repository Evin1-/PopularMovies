package mx.evin.udacity.popularmovies;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import mx.evin.udacity.popularmovies.entities.Result;
import mx.evin.udacity.popularmovies.fragments.DetailsFragment;
import mx.evin.udacity.popularmovies.fragments.PlaceholderFragment;
import mx.evin.udacity.popularmovies.utils.Constants;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = Constants.TAG_DETAILS;

    private PlaceholderFragment mPlaceholderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: 2/11/16 Style details buttons

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

        Bundle b = getIntent().getExtras();
        Result movie = b.getParcelable("movie");

        mPlaceholderFragment = (PlaceholderFragment) getSupportFragmentManager().findFragmentById(R.id.placeholderFragment);

        if (movie == null){
            Toast.makeText(DetailsActivity.this, R.string.retrieveMovieFailedMessage, Toast.LENGTH_SHORT).show();
            this.finish();
        }else{
            mPlaceholderFragment.refreshContent(movie);

            DetailsFragment af = DetailsFragment.newInstance(movie);
            getSupportFragmentManager().beginTransaction().add(af, "about_fragment").commit();
        }
    }
}
