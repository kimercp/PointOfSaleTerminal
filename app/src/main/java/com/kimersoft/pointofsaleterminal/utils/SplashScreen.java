package com.kimersoft.pointofsaleterminal.utils;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.widget.RelativeLayout;

import com.kimersoft.pointofsaleterminal.R;

/**
 * Created by Workstation on 11/02/2018.
 */

public class SplashScreen {

    private Activity activity;

    // constructor with activity argument and time in miliseconds
    public SplashScreen(Activity activity, int splashScreenDuration) {

        this.activity = activity;
        RelativeLayout secondLogo = (RelativeLayout) activity.findViewById(R.id.rl_sign_in);
        // Make the objects startSplashScreen
        ObjectAnimator signInLayout = ObjectAnimator.ofFloat(secondLogo, "alpha", 0f, 1f);
        signInLayout.setDuration(splashScreenDuration); // time in miliseconds
        signInLayout.start();
    }
}
