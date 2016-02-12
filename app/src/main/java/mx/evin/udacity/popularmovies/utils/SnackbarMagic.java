package mx.evin.udacity.popularmovies.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

import mx.evin.udacity.popularmovies.R;

/**
 * Created by evin on 2/11/16.
 */
public class SnackbarMagic {
    public static void showSnackbar(View anchor, int messageId) {
        Snackbar snackbar = Snackbar.make(anchor, messageId, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
