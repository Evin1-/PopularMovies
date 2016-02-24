package mx.evin.udacity.popularmovies.tasks;

import android.os.AsyncTask;

import java.util.List;

import mx.evin.udacity.popularmovies.entities.VideoPage;
import mx.evin.udacity.popularmovies.entities.VideoResult;
import mx.evin.udacity.popularmovies.fragments.DetailsFragment;
import mx.evin.udacity.popularmovies.network.MoviesRetrofit;

/**
 * Created by evin on 2/22/16.
 */
public class RetrieveVideosTask extends AsyncTask<Integer, Void, List<VideoResult>> {

    DetailsFragment mDetailsFragment;

    public RetrieveVideosTask(DetailsFragment detailsFragment) {
        mDetailsFragment = detailsFragment;
    }

    @Override
    protected List<VideoResult> doInBackground(Integer... params) {
        String movieId = (params.length > 0) ? String.valueOf(params[0]) : "293660";

        MoviesRetrofit moviesRetrofit = new MoviesRetrofit();

        VideoPage videoPage = moviesRetrofit.getVideos(movieId);

        return (videoPage!= null) ? videoPage.getVideoResults() : null;
    }

    @Override
    protected void onPostExecute(List<VideoResult> videoResults) {
        super.onPostExecute(videoResults);

        if (!isCancelled() && mDetailsFragment != null){
            mDetailsFragment.refreshVideos(videoResults);
        }

        clearReferences();
    }

    private void clearReferences() {
        mDetailsFragment = null;
    }
}
