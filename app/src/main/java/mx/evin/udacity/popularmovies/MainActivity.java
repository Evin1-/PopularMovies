package mx.evin.udacity.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "PopularMoviesTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queryMovieAPI("popularity");
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
                if (item.getTitle().equals("Order by popularity")){
                    item.setTitle(getString(R.string.menu_toggle_rating));
                    queryMovieAPI("popularity");
                    new RetrieveMovies(this).execute("popularity");
                }else{
                    item.setTitle(getString(R.string.menu_toggle_popularity));
                    new RetrieveMovies(this).execute("vote_average");
                    queryMovieAPI("vote_average");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void queryMovieAPI(String arg){
        new RetrieveMovies(this).execute(arg);
    }
}
