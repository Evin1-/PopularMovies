package mx.evin.udacity.popularmovies.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import mx.evin.udacity.popularmovies.database.MoviesContract;
import mx.evin.udacity.popularmovies.database.MoviesContract.FavoriteEntry;
import mx.evin.udacity.popularmovies.database.MoviesDbHelper;

public class FavoritesProvider extends ContentProvider {

    public static final String PROVIDER_AUTHORITY = MoviesContract.PROVIDER_AUTHORITY;
    public static final String PROVIDER_TABLE = FavoriteEntry.TABLE_NAME;
    public static final String PROVIDER_URL = "content://" + PROVIDER_AUTHORITY + "/" + PROVIDER_TABLE;
    public static final Uri PROVIDER_URI = Uri.parse(PROVIDER_URL);

    private static final String COUNT_SQL = "SELECT COUNT(*) FROM ";

    private MoviesDbHelper mDbHelper;

    static final int MOVIES = 1;
    static final int MOVIE_ID = 2;
    static final int MOVIE_COUNT = 3;

    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_AUTHORITY, PROVIDER_TABLE, MOVIES);
        uriMatcher.addURI(PROVIDER_AUTHORITY, PROVIDER_TABLE + "/#", MOVIE_ID);
        uriMatcher.addURI(PROVIDER_AUTHORITY, PROVIDER_TABLE + "/count", MOVIE_COUNT);
    }

    public FavoritesProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = mDbHelper.getWritableDatabase();

        int rowsChanged = sqLiteDatabase.delete(PROVIDER_TABLE, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return rowsChanged;
    }

    @Override
    public String getType(Uri uri) {
       switch (uriMatcher.match(uri)){
           case MOVIES:
               return FavoriteEntry.DIR_BASE_TYPE;
           case MOVIE_ID:
               return FavoriteEntry.ITEM_BASE_TYPE;
           case MOVIE_COUNT:
               return FavoriteEntry.ITEM_BASE_TYPE;
           default:
               throw new UnsupportedOperationException("Not yet implemented");
       }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase sqLiteDatabase = mDbHelper.getWritableDatabase();

        long row = sqLiteDatabase.insert(PROVIDER_TABLE, null, values);

        Uri appendedUri = ContentUris.withAppendedId(PROVIDER_URI, row);
        getContext().getContentResolver().notifyChange(appendedUri, null);

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
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(PROVIDER_TABLE);
        Cursor cursor;

        switch (uriMatcher.match(uri)) {
            case MOVIES:
                cursor = queryBuilder.query(sqLiteDatabase, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case MOVIE_ID:
                queryBuilder.appendWhere(FavoriteEntry.COLUMN_MOVIE_ID + " = " + uri.getPathSegments().get(1));
                cursor = queryBuilder.query(sqLiteDatabase, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case MOVIE_COUNT:
                cursor = sqLiteDatabase.rawQuery(COUNT_SQL + PROVIDER_TABLE, null);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
