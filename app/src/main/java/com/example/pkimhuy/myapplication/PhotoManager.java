package com.example.pkimhuy.myapplication;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

/**
 * Created by pkimhuy on 5/29/2017.
 */

public class PhotoManager {
    private Handler mHandler;

    /**
     * Call this constructor in main thread to associate handler with the UI thread
     */
    public PhotoManager() {
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                PhotoTask photoTask = (PhotoTask)msg.obj;
                Bitmap bitmap = photoTask.getImage();
                ImageView imageView = photoTask.getImageView();
                imageView.setImageBitmap(bitmap);
            }
        };
    }

    /**
     * Send message to main thread's message queue
     * @param photoTask Include downloaded image and reference to ImageView object
     */
    public void handleDownload(PhotoTask photoTask) {
        Message message = Message.obtain();
        message.obj = photoTask;
        mHandler.sendMessage(message);
    }
}
