package mx.evin.udacity.popularmovies.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import mx.evin.udacity.popularmovies.database.MoviesContract;
import mx.evin.udacity.popularmovies.database.MoviesContract.FavoriteEntry;

import mx.evin.udacity.popularmovies.database.MoviesDbHelper;

public class FavoritesProvider extends ContentProvider {

    static final String PROVIDER_AUTHORITY = MoviesContract.PROVIDER_AUTHORITY;
    static final String PROVIDER_TABLE = FavoriteEntry.TABLE_NAME;
    static final String URL = "content://" + PROVIDER_AUTHORITY + "/" + PROVIDER_TABLE;
    static final Uri CONTENT_URI = Uri.parse(URL);

    private MoviesDbHelper mDbHelper;

    private Context mContext;

    static final int MOVIES = 1;
    static final int MOVIE_ID = 2;

    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_AUTHORITY, PROVIDER_TABLE, MOVIES);
        uriMatcher.addURI(PROVIDER_AUTHORITY, PROVIDER_TABLE + "/#", MOVIE_ID);
    }


    public FavoritesProvider() {
        mContext = getContext();
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
       switch (uriMatcher.match(uri)){
           case MOVIES:
               return FavoriteEntry.DIR_BASE_TYPE;
           case MOVIE_ID:
               return FavoriteEntry.ITEM_BASE_TYPE;
           default:
               throw new UnsupportedOperationException("Not yet implemented");
       }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase sqLiteDatabase = mDbHelper.getWritableDatabase();

        long row = sqLiteDatabase.insert(PROVIDER_TABLE, null, values);

        Uri appendedUri = ContentUris.withAppendedId(CONTENT_URI, row);
        mContext.getContentResolver().notifyChange(appendedUri, null);

        return appendedUri;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new MoviesDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase sqLiteDatabase = mDbHelper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(PROVIDER_TABLE);

        switch (uriMatcher.match(uri)) {
            case MOVIES:
                break;
            case MOVIE_ID:
                qb.appendWhere(FavoriteEntry._ID + " = " + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        Cursor cursor = qb.query(sqLiteDatabase, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(mContext.getContentResolver(), uri);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
