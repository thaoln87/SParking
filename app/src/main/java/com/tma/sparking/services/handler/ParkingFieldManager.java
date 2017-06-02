package com.tma.sparking.services.handler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.tma.sparking.models.ParkingField;
import com.tma.sparking.services.ParkingFieldService;
import com.tma.sparking.services.http.Parking;

import java.util.List;

public class ParkingFieldManager {
    private Handler mMainThreadHandler;
    private GetParkingFieldTask mGetParkingFieldTask;
    private GetParkingFieldCallback mGetParkingFieldCallback;
    private long mDelayMillis;
    private long mChannelId;

    public ParkingFieldManager(Context context) {
        mMainThreadHandler = new Handler(context.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (!mGetParkingFieldTask.hasError()) {
                    List<ParkingField> parkingFields = mGetParkingFieldTask.getParkingFields();
                    mGetParkingFieldCallback.onParkingFieldsLoaded(parkingFields);
                } else {
                    mGetParkingFieldCallback.onDataUnavailable(mGetParkingFieldTask.getErrorMessage());
                }
            }
        };

        mDelayMillis = 0;
    }

    public void setGetParkingFieldCallback(GetParkingFieldCallback callback) {
        mGetParkingFieldCallback = callback;
    }

    public void startLoading() {
        mGetParkingFieldTask = new GetParkingFieldTask(mChannelId);

        ParkingFieldHandlerThread thread = new ParkingFieldHandlerThread(new ParkingFieldService(), mMainThreadHandler);
        thread.setDelayMillis(mDelayMillis);
        thread.setGetParkingFieldTask(mGetParkingFieldTask);
        thread.start();
    }

    public void setDelayMillis(long delayMillis) {
        mDelayMillis = delayMillis;
    }

    public void setChannelId(long channelId) {
        mChannelId = channelId;
    }
}
