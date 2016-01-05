package mx.evin.udacity.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import mx.evin.udacity.popularmovies.entities.Page;
import mx.evin.udacity.popularmovies.entities.Result;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by evin on 1/3/16.
 */
public class RetrieveMovies extends AsyncTask<String, String, Void>{

    private final String TAG = "PopularMoviesAsyncTAG";
    private AppCompatActivity mActivity;
    private TextView textView;

    public RetrieveMovies(AppCompatActivity activity) {
        mActivity = activity;
        textView = (TextView) mActivity.findViewById(R.id.main_id_txt);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        textView.setText("");
    }

    @Override
    protected Void doInBackground(String... params) {
        String order;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieDBService service = retrofit.create(MovieDBService.class);
        order = (params.length < 1) ? "popularity.desc" : params[0] + ".desc";

        Call<Page> listCall = service.listMovies(order, Constants.MDB_API_KEY);

        try{
            //TODO: Change this for actual magic
            Page results = listCall.execute().body();
            for (Result result : results.getResults()) {
                publishProgress(result.getTitle());
            }
        }catch (Exception e){
            Log.e(TAG, "Error: " + e.toString());
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        String text = textView.getText().toString();
        textView.setText(text + "\n" + values[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        String text = textView.getText().toString();
        textView.setText(text + "\nFinished!!");
        Log.d(TAG, "Finished");
    }

    public interface MovieDBService {
        @GET("/3/discover/movie?vote_count.gte=100")
        Call<Page> listMovies(@Query("sort_by") String sort_by, @Query("api_key") String api_key);
    }
}
