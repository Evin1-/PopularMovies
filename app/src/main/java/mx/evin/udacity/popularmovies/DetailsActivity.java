package mx.evin.udacity.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import mx.evin.udacity.popularmovies.entities.Result;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = Constants.TAG_DETAILS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String base_url = "http://image.tmdb.org/t/p/w500/";

        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            Log.e(TAG, e.toString());
        }

        ImageView imageView = (ImageView) findViewById(R.id.detailsBackgroundImage);

        Bundle b = getIntent().getExtras();
        Result movie = b.getParcelable("movie");
        Log.d(TAG, movie.getTitle());
        Picasso.with(this).load(base_url + movie.getPosterPath()).into(imageView);
    }
}
