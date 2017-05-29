package com.example.pkimhuy.myapplication;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Created by pkimhuy on 5/29/2017.
 */

public class PhotoDownloadHandlerThread extends HandlerThread {
    private Handler mHandler;
    private String mImageUrl;
    private PhotoTask mPhotoTask;

    public PhotoDownloadHandlerThread(String name, String imageUrl, PhotoTask photoTask) {
        super(name);
        mImageUrl = imageUrl;
        mPhotoTask = photoTask;
    }

    @Override
    protected void onLooperPrepared() {
        mHandler = new Handler(getLooper());
        mHandler.postDelayed(new PhotoDownloadRunnable(mImageUrl, mPhotoTask), 20000);
    }
}
