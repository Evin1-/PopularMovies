package mx.evin.udacity.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = Constants.TAG_DETAILS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().hide();

        ImageView imageView = (ImageView) findViewById(R.id.detailsBackgroundImage);

        Bundle b = getIntent().getExtras();
        String url = b.getString("key");
//        Log.d(TAG, url);
        Picasso.with(this).load(url).into(imageView);
    }
}
