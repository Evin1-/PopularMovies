package mx.evin.udacity.popularmovies;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import mx.evin.udacity.popularmovies.entities.Result;
import mx.evin.udacity.popularmovies.fragments.DetailsFragment;
import mx.evin.udacity.popularmovies.utils.Constants;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = Constants.TAG_DETAILS;

    @Bind(R.id.detailsBackgroundImage)
    ImageView mImageView;

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

        if (movie == null){
            Toast.makeText(DetailsActivity.this, R.string.retrieveMovieFailedMessage, Toast.LENGTH_SHORT).show();
            this.finish();
        }else{
            Picasso.with(this).load(Constants.BASE_IMG_URL + movie.getPosterPath()).into(mImageView);

            DetailsFragment af = DetailsFragment.newInstance(movie);
            getSupportFragmentManager().beginTransaction().add(af, "about_fragment").commit();
        }
    }
}
