package mx.evin.udacity.popularmovies.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mx.evin.udacity.popularmovies.R;
import mx.evin.udacity.popularmovies.entities.Result;
import mx.evin.udacity.popularmovies.utils.Constants;
import mx.evin.udacity.popularmovies.utils.SnackbarMagic;


/**
 * A simple {@link DialogFragment} subclass.
 */
public class DetailsFragment extends Fragment {

    @Bind(R.id.detailsFragmentTitleTxt)
    TextView mTextViewTitle;
    @Bind(R.id.detailsFragmentReleaseTxt)
    TextView mTextViewRelease;
    @Bind(R.id.detailsFragmentVote)
    TextView mTextViewVote;
    @Bind(R.id.detailsFragmentPlot)
    TextView mTextViewPlot;
    @Bind(R.id.detailsFragmentPoster)
    ImageView mImageView;

    public DetailsFragment() {

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
        Activity activity = getActivity();
        if (getView() != null) {
            SnackbarMagic.showSnackbar(getView().getRootView(), R.string.addedToFavoritesSuccess);
        }
    }

    @OnClick(R.id.viewOnYoutubeBtn)
    public void viewOnYoutubeClick() {
        Activity activity = getActivity();
        if (getView() != null) {
            SnackbarMagic.showSnackbar(getView().getRootView(), R.string.openingYoutubeApp);
        }
    }

    public void refreshDetails(Result movie) {
        if (movie == null) {
            if (getView() != null){
                SnackbarMagic.showSnackbar(getView().getRootView(), R.string.retrieveMovieFailedMessage);
            }
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        } else {
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
    }

}
