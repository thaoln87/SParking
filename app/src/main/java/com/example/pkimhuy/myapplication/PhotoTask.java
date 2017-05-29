package com.example.pkimhuy.myapplication;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by pkimhuy on 5/29/2017.
 */

public class PhotoTask {
    private Bitmap mImage;
    private ImageView mImageView;
    private PhotoManager mPhotoManager;

    public PhotoTask(PhotoManager photoManager) {
        mPhotoManager = photoManager;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap image) {
        mImage = image;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public void setImageView(ImageView imageView) {
        mImageView = imageView;
    }

    public void handleDownload() {
        mPhotoManager.handleDownload(this);
    }
}
