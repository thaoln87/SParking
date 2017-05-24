package com.tma.sparking;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreenActivity extends AppCompatActivity {
    private Handler showSplashScreen = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fullScreen();
        setContentView(R.layout.activity_splash_screen);

        int SPLASH_TIME_OUT = 3 * 1000;
        showSplashScreen.postDelayed(showSplashRunnable, SPLASH_TIME_OUT);
        carMovingAnimation();
    }

    private void carMovingAnimation(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        ImageView carImageView = (ImageView)findViewById(R.id.img_car);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(carImageView, "x", width);
        objectAnimator.setDuration(3000);
        objectAnimator.start();
    }

    private void fullScreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    Runnable showSplashRunnable = new Runnable() {
        @Override
        public void run() {
        Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(i);
        SplashScreenActivity.this.finish();

        }
    };
}
