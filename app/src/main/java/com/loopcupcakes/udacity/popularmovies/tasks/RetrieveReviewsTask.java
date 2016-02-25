package com.loopcupcakes.udacity.popularmovies.tasks;

import android.os.AsyncTask;

import com.loopcupcakes.udacity.popularmovies.entities.ReviewPage;
import com.loopcupcakes.udacity.popularmovies.entities.ReviewResult;
import com.loopcupcakes.udacity.popularmovies.fragments.DetailsFragment;
import com.loopcupcakes.udacity.popularmovies.network.MoviesRetrofit;

import java.util.List;

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
