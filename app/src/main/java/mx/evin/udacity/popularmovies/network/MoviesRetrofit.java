package mx.evin.udacity.popularmovies.network;

import android.util.Log;

import mx.evin.udacity.popularmovies.entities.Page;
import mx.evin.udacity.popularmovies.entities.ReviewPage;
import mx.evin.udacity.popularmovies.entities.VideoPage;
import mx.evin.udacity.popularmovies.utils.Constants;
import mx.evin.udacity.popularmovies.utils.Keys;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by evin on 2/22/16.
 */
public class MoviesRetrofit {

    private static final String TAG = "MoviesRetrofit";
    private static final Retrofit retrofit;
    private static final MovieDBService service;

    static {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(MovieDBService.class);
    }

    public Page getMovies(String order) {
        Call<Page> listCall = service.listMovies(order, Keys.MDB_API_KEY);

        Page results = null;

        try {
            results = listCall.execute().body();
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.toString());
        }

        return results;
    }

    public VideoPage getVideos(String movieId){
        Call<VideoPage> listCall = service.listVideos(movieId, Keys.MDB_API_KEY);

        VideoPage results = null;

        try {
            results = listCall.execute().body();
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.toString());
        }

        return results;
    }

    public ReviewPage getReviews(String movieId){
        Call<ReviewPage> listCall = service.listReviews(movieId, Keys.MDB_API_KEY);

        ReviewPage results = null;

        try {
            results = listCall.execute().body();
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.toString());
        }

        return results;
    }

}
