package mx.evin.udacity.popularmovies.tasks;

import android.os.AsyncTask;

import java.util.List;

import mx.evin.udacity.popularmovies.MainActivity;
import mx.evin.udacity.popularmovies.entities.Page;
import mx.evin.udacity.popularmovies.entities.Result;
import mx.evin.udacity.popularmovies.network.MoviesRetrofit;

/**
 * Created by evin on 1/3/16.
 */
public class RetrieveMoviesTask extends AsyncTask<String, Void, List<Result>> {

    private MainActivity mActivity;

    public RetrieveMoviesTask(MainActivity activity) {
        mActivity = activity;
    }

    @Override
    protected List<Result> doInBackground(String... params) {
        String order = (params.length < 1) ? "popularity.desc" : params[0] + ".desc";

        MoviesRetrofit moviesRetrofit = new MoviesRetrofit();

        Page results = moviesRetrofit.getMovies(order);

        return (results != null) ? results.getResults() : null;
    }

    @Override
    protected void onPostExecute(List<Result> results) {
        super.onPostExecute(results);

        if (!isCancelled() && mActivity != null) {
            mActivity.setResults(results);
        }

        clearReferences();
    }

    private void clearReferences() {
        mActivity = null;
    }
}
