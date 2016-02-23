package mx.evin.udacity.popularmovies.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import mx.evin.udacity.popularmovies.fragments.MainFragment;
import mx.evin.udacity.popularmovies.utils.NetworkMagic;

/**
 * Created by evin on 2/22/16.
 */
public class NetworkReceiver extends BroadcastReceiver {

    private static final String TAG = "NetworkReceiverTAG_";
    MainFragment mMainFragment;

    public NetworkReceiver(MainFragment mainFragment) {
        mMainFragment = mainFragment;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + intent.getAction());
        if (mMainFragment != null && NetworkMagic.isNetworkAvailable(mMainFragment.getActivity())){
            if (mMainFragment.isActuallyEmpty()){
                mMainFragment.getActivityCallback().onEmptyResults();
            }
        }
    }
}
