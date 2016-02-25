package com.loopcupcakes.udacity.popularmovies;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by evin on 2/11/16.
 */
public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

//        Fabric.with(this, new Crashlytics());
        LeakCanary.install(this);
    }
}
