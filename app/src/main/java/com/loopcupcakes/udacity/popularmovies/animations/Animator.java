package com.loopcupcakes.udacity.popularmovies.animations;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by evin on 2/24/16.
 */
public class Animator {

    public void fadeOut(final View view, final int duration) {
        if (view != null) {
            AlphaAnimation fadeOutAnimation = fadeOutSkeleton(view, duration);
            view.startAnimation(fadeOutAnimation);
        }
    }

    @NonNull
    private AlphaAnimation fadeOutSkeleton(final View view, int duration) {
        AlphaAnimation fadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);
        fadeOutAnimation.setDuration(duration);
        fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return fadeOutAnimation;
    }
}