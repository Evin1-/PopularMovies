package mx.evin.udacity.popularmovies.network;

import mx.evin.udacity.popularmovies.entities.Page;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by evin on 2/22/16.
 */
public interface MovieDBService {
    @GET("/3/discover/movie?vote_count.gte=100")
    Call<Page> listMovies(@Query("sort_by") String sort_by, @Query("api_key") String api_key);
}