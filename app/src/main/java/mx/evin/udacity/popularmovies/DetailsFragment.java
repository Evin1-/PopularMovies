package mx.evin.udacity.popularmovies;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import mx.evin.udacity.popularmovies.entities.Result;


/**
 * A simple {@link DialogFragment} subclass.
 */
public class DetailsFragment extends DialogFragment {

    public DetailsFragment() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Result movie = getArguments().getParcelable("movie");

        if (movie == null){
            Toast.makeText(getActivity(), "Failed to retrieve movie", Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.detailsFragmentPoster);
        Picasso.with(getContext()).load(Constants.BASE_IMG_URL + movie.getBackdropPath()).into(imageView);

        ((TextView) view.findViewById(R.id.detailsFragmentTitleTxt)).setText(movie.getTitle());
        ((TextView) view.findViewById(R.id.detailsFragmentReleaseTxt)).setText(movie.getReleaseDate());
        ((TextView) view.findViewById(R.id.detailsFragmentVote)).setText(String.format("%.02f", movie.getVoteAverage()));
        ((TextView) view.findViewById(R.id.detailsFragmentPlot)).setText(movie.getOverview());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().finish();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
