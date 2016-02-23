package mx.evin.udacity.popularmovies.tasks;

import android.os.AsyncTask;

import java.util.ArrayList;

import mx.evin.udacity.popularmovies.MainActivity;
import mx.evin.udacity.popularmovies.entities.Page;
import mx.evin.udacity.popularmovies.entities.Result;
import mx.evin.udacity.popularmovies.network.MoviesRetrofit;

/**
 * Created by evin on 1/3/16.
 */
public class RetrieveMoviesTask extends AsyncTask<String, Result, Void> {
    // TODO: 2/22/16 Return List<Page> avoid onProgressUpdate

    private static final String TAG = "RetrieveTaskTAG_";
    private MainActivity mActivity;
    private ArrayList<Result> mResults;

    public RetrieveMoviesTask(MainActivity activity) {
        mActivity = activity;
        mResults = new ArrayList<>();
    }

    @Override
    protected Void doInBackground(String... params) {
        String order = (params.length < 1) ? "popularity.desc" : params[0] + ".desc";

        MoviesRetrofit moviesRetrofit = new MoviesRetrofit();

        Page results = moviesRetrofit.getMovies(order);

        for (Result result : results.getResults()) {
            publishProgress(result);
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

        if (!isCancelled() && mActivity != null) {
            mActivity.setResults(mResults);
        }
        clearReferences();
    }

    private void clearReferences() {
        mActivity = null;
        mResults = null;
    }
}
