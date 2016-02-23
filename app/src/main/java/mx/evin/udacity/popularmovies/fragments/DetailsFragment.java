package mx.evin.udacity.popularmovies.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mx.evin.udacity.popularmovies.FavoritesActivity;
import mx.evin.udacity.popularmovies.R;
import mx.evin.udacity.popularmovies.database.MoviesContract.FavoriteEntry;
import mx.evin.udacity.popularmovies.entities.Result;
import mx.evin.udacity.popularmovies.providers.FavoritesProvider;
import mx.evin.udacity.popularmovies.utils.Constants;
import mx.evin.udacity.popularmovies.utils.SnackbarMagic;


/**
 * A simple {@link DialogFragment} subclass.
 */
public class DetailsFragment extends Fragment {
    // TODO: 2/22/16 Add undo button when changing Favorites

    private static final String TAG = "DetailsFragmentTAG_";

    @Bind(R.id.detailsFragmentTitleTxt)
    TextView mTextViewTitle;
    @Bind(R.id.detailsFragmentReleaseTxt)
    TextView mTextViewRelease;
    @Bind(R.id.detailsFragmentVote)
    TextView mTextViewVote;
    @Bind(R.id.detailsFragmentPlot)
    TextView mTextViewPlot;
    @Bind(R.id.detailsFragmentIsFavoriteIcon)
    ImageView mImageIcon;
    @Bind(R.id.detailsFragmentPoster)
    ImageView mImageView;
    @Bind(R.id.addToFavoritesBtn)
    Button mButtonFavorites;

    private ActivityCallback mCallback;

    private Result mMovie;
    private boolean isFavorite = false;

    public DetailsFragment() {

    }

    public interface ActivityCallback{
        void onModifiedFavorites();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof FavoritesActivity){
            mCallback = (ActivityCallback) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.addToFavoritesBtn)
    public void onAddToFavoritesBtnClick() {
        if (getView() != null) {
            if (isFavorite) {
                SnackbarMagic.showSnackbar(getView(), R.string.removedFromFavoritesSuccess);
                removeFromFavorites();
            } else {
                SnackbarMagic.showSnackbar(getView(), R.string.addedToFavoritesSuccess);
                addToFavorites();
            }
        }

        checkIfFavorite();
        modifyUIFavorite();
    }

    private void removeFromFavorites() {
        final ContentResolver contentResolver = getActivity().getContentResolver();
        final String[] selectionArgs = {String.valueOf(mMovie.getId())};
        final String columnMovieId = FavoriteEntry.COLUMN_MOVIE_ID + " = ?";

        int nRows = contentResolver.delete(FavoritesProvider.PROVIDER_URI, columnMovieId, selectionArgs);
        if (nRows > 0 && mCallback != null){
            mCallback.onModifiedFavorites();
        }
    }

    private void addToFavorites() {
        final ContentResolver contentResolver = getActivity().getContentResolver();
        final ContentValues values = FavoriteEntry.resolveMovie(mMovie);

        Uri uri = contentResolver.insert(FavoritesProvider.PROVIDER_URI, values);
        if (uri != null && mCallback != null){
            mCallback.onModifiedFavorites();
        }
    }

    @OnClick(R.id.viewOnYoutubeBtn)
    public void viewOnYoutubeClick() {
        Activity activity = getActivity();
        if (getView() != null) {
            SnackbarMagic.showSnackbar(getView(), R.string.openingYoutubeApp);
        }
    }

    public void refreshDetails(Result movie) {
        if (movie == null) {
            if (getView() != null) {
                SnackbarMagic.showSnackbar(getView().getRootView(), R.string.retrieveMovieFailedMessage);
            }
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        } else {

            mMovie = movie;

            checkIfFavorite();
            modifyUIFavorite();
            refreshUI(movie);
        }
    }

    private void modifyUIFavorite() {
        if (isFavorite) {
            mButtonFavorites.setText(R.string.removeFromFavorites);
            mImageIcon.setVisibility(View.VISIBLE);
        } else {
            mButtonFavorites.setText(R.string.addToFavorites);
            mImageIcon.setVisibility(View.INVISIBLE);
        }
    }

    public void refreshUI(Result movie) {
        Picasso.with(getContext())
                .load(Constants.BASE_IMG_URL + movie.getBackdropPath())
                .placeholder(R.drawable.large_placeholder)
                .into(mImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        mImageView.setVisibility(View.INVISIBLE);
                    }
                });

        mTextViewTitle.setText(movie.getTitle());
        mTextViewRelease.setText(movie.getReleaseDate());
        mTextViewVote.setText(String.format("%.02f", movie.getVoteAverage()));
        mTextViewPlot.setText(movie.getOverview());
    }

    private void checkIfFavorite() {
        if (mMovie == null) {
            return;
        }
        Uri uri = Uri.withAppendedPath(FavoritesProvider.PROVIDER_URI, String.valueOf(mMovie.getId()));
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);

        if (cursor != null) {
            isFavorite = cursor.getCount() > 0;
            cursor.close();
        }
    }

}
