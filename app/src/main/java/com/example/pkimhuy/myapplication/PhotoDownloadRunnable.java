package com.example.pkimhuy.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Process;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pkimhuy on 5/29/2017.
 */

public class PhotoDownloadRunnable implements Runnable {
    private String mImageUrl;
    private PhotoTask mPhotoTask;

    public PhotoDownloadRunnable(String imageUrl, PhotoTask photoTask) {
        mImageUrl = imageUrl;
        mPhotoTask = photoTask;
    }

    @Override
    public void run() {
        try {
            Log.d("handler_demo", "calleddddd");

            URL url = new URL(mImageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream is = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);

            mPhotoTask.setImage(bitmap);
            mPhotoTask.handleDownload();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
