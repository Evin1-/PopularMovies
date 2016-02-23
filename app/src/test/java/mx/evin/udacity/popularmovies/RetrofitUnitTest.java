package mx.evin.udacity.popularmovies;

import org.junit.Test;

import mx.evin.udacity.popularmovies.entities.Page;
import mx.evin.udacity.popularmovies.entities.Result;
import mx.evin.udacity.popularmovies.entities.ReviewPage;
import mx.evin.udacity.popularmovies.entities.ReviewResult;
import mx.evin.udacity.popularmovies.entities.VideoPage;
import mx.evin.udacity.popularmovies.entities.VideoResult;
import mx.evin.udacity.popularmovies.network.MoviesRetrofit;

import static org.junit.Assert.assertTrue;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class RetrofitUnitTest {
    @Test
    public void retrofit_isCorrect() throws Exception {
        MoviesRetrofit moviesRetrofit = new MoviesRetrofit();

        Page page = moviesRetrofit.getMovies("popularity");
        ReviewPage reviewPage = moviesRetrofit.getReviews("293660");
        VideoPage videoPage = moviesRetrofit.getVideos("87101");

        assertTrue(page.getResults().size() > 0);
        for (Result result : page.getResults()){
            System.out.println(result.getTitle());
        }

        System.out.println("");

        assertTrue(reviewPage.getReviewResults().size() > 0);
        for (ReviewResult reviewResult : reviewPage.getReviewResults()){
            System.out.println(reviewResult.getContent());
        }

        System.out.println("");

        assertTrue(videoPage.getVideoResults().size() > 0);
        for (VideoResult videoResult : videoPage.getVideoResults()){
            System.out.println(videoResult.getKey());
        }
    }
}