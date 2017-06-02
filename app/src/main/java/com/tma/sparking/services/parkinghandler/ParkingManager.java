package com.tma.sparking.services.parkinghandler;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.LoaderManager;

public class ParkingManager {
    private Context mContext;
    private LoaderManager mLoaderManager;
    private ParkingDataCallback mParkingDataCallback;

    public ParkingManager(Context context, LoaderManager loaderManager) {
        mContext = context;
        mLoaderManager = loaderManager;
    }

    public void startLoading() {
        mContext.startService(new Intent(mContext, ParkingHandlerService.class));

        ParkingLoaderCallback loaderCallback = new ParkingLoaderCallback(mContext, mParkingDataCallback);
        mLoaderManager.initLoader(0, null, loaderCallback);
    }

    public void setParkingDataCallback(ParkingDataCallback parkingDataCallback) {
        mParkingDataCallback = parkingDataCallback;
    }
}
