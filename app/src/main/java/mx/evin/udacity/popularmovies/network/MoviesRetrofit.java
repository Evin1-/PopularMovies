package mx.evin.udacity.popularmovies.network;

import android.util.Log;

import mx.evin.udacity.popularmovies.entities.Page;
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

    public Page getMovies(String order) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieDBService service = retrofit.create(MovieDBService.class);


        Call<Page> listCall = service.listMovies(order, Keys.MDB_API_KEY);

        Page results = null;

        try {
            results = listCall.execute().body();
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.toString());
        }

        return results;
    }
}
