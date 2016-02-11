package mx.evin.udacity.popularmovies;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mx.evin.udacity.popularmovies.entities.Result;


/**
 * A simple {@link DialogFragment} subclass.
 */
public class DetailsFragment extends DialogFragment {

    // TODO: 2/11/16 Try SnackBar with FullScreenFragment

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

    public static DetailsFragment newInstance(Result movie) {

        Bundle args = new Bundle();

        DetailsFragment fragment = new DetailsFragment();
        args.putParcelable("movie", movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.addToFavoritesBtn)
    public void onAddToFavoritesBtnClick(){
        Activity activity = getActivity();
        Toast.makeText(activity, mTextViewTitle.getText() + activity.getString(R.string.addedToFavoritesSuccess), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.viewOnYoutubeBtn)
    public void viewOnYoutubeClick(){
        Activity activity = getActivity();
        Toast.makeText(activity, activity.getString(R.string.openingYoutubeApp), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Result movie = getArguments().getParcelable("movie");

        if (movie == null) {
            Toast.makeText(getActivity(), R.string.retrieveMovieFailedMessage, Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        } else {
            Picasso.with(getContext())
                    .load(Constants.BASE_IMG_URL + movie.getBackdropPath())
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().finish();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
