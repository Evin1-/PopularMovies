package mx.evin.udacity.popularmovies.tasks;

import android.os.AsyncTask;

import java.util.List;

import mx.evin.udacity.popularmovies.entities.ReviewPage;
import mx.evin.udacity.popularmovies.entities.ReviewResult;
import mx.evin.udacity.popularmovies.fragments.DetailsFragment;
import mx.evin.udacity.popularmovies.network.MoviesRetrofit;

/**
 * Created by evin on 2/22/16.
 */
public class RetrieveReviewsTask extends AsyncTask<Integer, Void, List<ReviewResult>> {

    DetailsFragment mDetailsFragment;

    public RetrieveReviewsTask(DetailsFragment detailsFragment) {
        mDetailsFragment = detailsFragment;
    }

    @Override
    protected List<ReviewResult> doInBackground(Integer... params) {
        String movieId = (params.length > 0) ? String.valueOf(params[0]) : "293660";

        MoviesRetrofit moviesRetrofit = new MoviesRetrofit();

        ReviewPage reviewPage = moviesRetrofit.getReviews(movieId);

        return (reviewPage != null) ? reviewPage.getReviewResults() : null;
    }

    @Override
    protected void onPostExecute(List<ReviewResult> reviewResults) {
        super.onPostExecute(reviewResults);

        if (!isCancelled() && mDetailsFragment != null){
            mDetailsFragment.refreshReviews(reviewResults);
        }

        clearReferences();
    }

    private void clearReferences() {
        mDetailsFragment = null;
    }
}
