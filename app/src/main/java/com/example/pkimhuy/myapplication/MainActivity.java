package com.example.pkimhuy.myapplication;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView)findViewById(R.id.myImage);

        PhotoManager photoManager = new PhotoManager();

        String imageUrl = "https://www.google.com.vn/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";
        PhotoTask photoTask = new PhotoTask(photoManager);
        photoTask.setImageView(imageView);

        HandlerThread thread = new PhotoDownloadHandlerThread("handler_thread", imageUrl, photoTask);
        thread.start();
    }
}
