package com.loopcupcakes.udacity.popularmovies.network;

import com.loopcupcakes.udacity.popularmovies.entities.Page;
import com.loopcupcakes.udacity.popularmovies.entities.ReviewPage;
import com.loopcupcakes.udacity.popularmovies.entities.VideoPage;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by evin on 2/22/16.
 */
public interface MovieDBService {
    @GET("/3/discover/movie?vote_count.gte=100")
    Call<Page> listMovies(@Query("sort_by") String sort_by,
                          @Query("api_key") String api_key,
                          @Query("primary_release_date.gte") String primary_release_date_gte);

    @GET("3/movie/{movie_id}/videos")
    Call<VideoPage> listVideos(@Path("movie_id") String movie_id,
                               @Query("api_key") String api_key,
                               @Query("primary_release_date.gte") String primary_release_date_gte);

    @GET("3/movie/{movie_id}/reviews")
    Call<ReviewPage> listReviews(@Path("movie_id") String movie_id,
                                 @Query("api_key") String api_key,
                                 @Query("primary_release_date.gte") String primary_release_date_gte);
}