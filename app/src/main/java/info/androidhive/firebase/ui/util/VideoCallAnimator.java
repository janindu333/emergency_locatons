package info.androidhive.firebase.ui.util;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

public class VideoCallAnimator {
    public static void slideToTop(View view){
        TranslateAnimation animate = new TranslateAnimation(0,0,0,-view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(false);
        view.startAnimation(animate);
        view.setVisibility(View.INVISIBLE);
    }

    public static void fadeOut(View view) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(250);

        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(fadeOut);
        view.setAnimation(animation);
        view.setVisibility(View.INVISIBLE);
    }

    public static void fadeIn(View view, int time) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(time);

        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(fadeIn);
        view.setAnimation(animation);
        view.setVisibility(View.VISIBLE);
    }
}
