package mx.evin.udacity.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mx.evin.udacity.popularmovies.entities.Page;
import mx.evin.udacity.popularmovies.entities.Result;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by evin on 1/3/16.
 */
public class RetrieveMovies extends AsyncTask<String, Result, Void>{

    private static final String TAG = Constants.TAG_ASYNC;
    private AppCompatActivity mActivity;
    private ArrayList<Result> mResults;
    private MoviesAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public RetrieveMovies(AppCompatActivity activity) {
        mActivity = activity;
        mResults = new ArrayList<>();
        mRecyclerView = (RecyclerView) mActivity.findViewById(R.id.rvMainResults);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        startRecycler(mResults);
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
                publishProgress(result);
            }
        }catch (Exception e){
            Log.e(TAG, "Error: " + e.toString());
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Result... values) {
        super.onProgressUpdate(values);
        mResults.add(values[0]);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d(TAG, "Finished");
    }

    public void startRecycler(List<Result> results){
        mAdapter = new MoviesAdapter(results);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
    }

    public interface MovieDBService {
        @GET("/3/discover/movie?vote_count.gte=100")
        Call<Page> listMovies(@Query("sort_by") String sort_by, @Query("api_key") String api_key);
    }
}
