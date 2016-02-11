package mx.evin.udacity.popularmovies;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by evin on 2/11/16.
 */
public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
