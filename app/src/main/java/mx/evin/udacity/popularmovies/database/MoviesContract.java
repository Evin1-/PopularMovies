package mx.evin.udacity.popularmovies.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.provider.BaseColumns;

import mx.evin.udacity.popularmovies.entities.Result;

/**
 * Created by evin on 2/19/16.
 */
public class MoviesContract {
    public static final String PROVIDER_AUTHORITY = "mx.evin.udacity.popularmovies.Movies";

    public static class FavoriteEntry implements BaseColumns{
        public static final String TABLE_NAME = "favorites";

        public static final String DIR_BASE_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + PROVIDER_AUTHORITY + "/" + TABLE_NAME;
        public static final String ITEM_BASE_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + PROVIDER_AUTHORITY + "/" + TABLE_NAME;

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_DATE = "release_date";
        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static ContentValues resolveMovie(Result movie) {
            ContentValues values = new ContentValues();

            values.put(COLUMN_TITLE, movie.getTitle());
            values.put(COLUMN_POSTER_PATH, movie.getPosterPath());
            values.put(COLUMN_BACKDROP_PATH, movie.getBackdropPath());
            values.put(COLUMN_MOVIE_ID, movie.getId());
            values.put(COLUMN_SYNOPSIS, movie.getOverview());
            values.put(COLUMN_RATING, movie.getVoteAverage());
            values.put(COLUMN_POPULARITY, movie.getPopularity());
            values.put(COLUMN_DATE, movie.getReleaseDate());

            return values;
        }

    }
}
