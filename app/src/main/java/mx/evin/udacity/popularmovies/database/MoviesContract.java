package mx.evin.udacity.popularmovies.database;

import android.provider.BaseColumns;

/**
 * Created by evin on 2/19/16.
 */
public class MoviesContract {

    public static class FavoriteEntry implements BaseColumns{
        public static final String TABLE_NAME = "favorites";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_DATE = "release_date";
        public static final String COLUMN_MOVIE_ID = "movie_id";
    }
}
