package mx.evin.udacity.popularmovies.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import mx.evin.udacity.popularmovies.MainActivity;
import mx.evin.udacity.popularmovies.entities.Page;
import mx.evin.udacity.popularmovies.entities.Result;
import mx.evin.udacity.popularmovies.utils.Constants;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by evin on 1/3/16.
 */
public class RetrieveMoviesTask extends AsyncTask<String, Result, Void>{

    private static final String TAG = Constants.TAG_ASYNC;
    private final MainActivity mActivity;
    private final ArrayList<Result> mResults;

    public RetrieveMoviesTask(MainActivity activity) {
        mActivity = activity;
        mResults = new ArrayList<>();
    }

    public interface MovieDBService {
        @GET("/3/discover/movie?vote_count.gte=100")
        Call<Page> listMovies(@Query("sort_by") String sort_by, @Query("api_key") String api_key);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        String order;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieDBService service = retrofit.create(MovieDBService.class);

        order = (params.length < 1) ? "popularity.desc" : params[0] + ".desc";

        Call<Page> listCall = service.listMovies(order, Constants.MDB_API_KEY);

        try{
            Page results = listCall.execute().body();
            for (Result result : results.getResults()) {
                publishProgress(result);
            }
        }catch (Exception e){
            Log.e(TAG, "Error: " + e.toString());
            Log.e(TAG, "Make sure you put your API key in Constants.java");
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Result... values) {
        super.onProgressUpdate(values);
        mResults.add(values[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d(TAG, "Finished");

        mActivity.setResults(mResults);
    }
}
