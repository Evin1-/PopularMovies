package mx.evin.udacity.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import mx.evin.udacity.popularmovies.entities.Result;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = Constants.TAG_DETAILS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            Log.e(TAG, e.toString());
        }

        ImageView imageView = (ImageView) findViewById(R.id.detailsBackgroundImage);

        Bundle b = getIntent().getExtras();
        Result movie = b.getParcelable("movie");

        if (movie == null){
            Toast.makeText(DetailsActivity.this, "Failed to retrieve movie", Toast.LENGTH_SHORT).show();
            this.finish();
        }

        Picasso.with(this).load(Constants.BASE_IMG_URL + movie.getPosterPath()).into(imageView);

        DetailsFragment af = DetailsFragment.newInstance(movie);
        getSupportFragmentManager().beginTransaction().add(af, "about_fragment").commit();

    }
}
