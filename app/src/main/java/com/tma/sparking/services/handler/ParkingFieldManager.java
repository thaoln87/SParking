package com.tma.sparking.services.handler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.tma.sparking.models.ParkingField;
import com.tma.sparking.services.ParkingFieldService;

import java.util.List;

public class ParkingFieldManager {
    private Handler mMainThreadHandler;
    private GetParkingFieldCallback mGetParkingFieldCallback;
    private long mDelayMillis;

    public ParkingFieldManager(Context context) {
        mMainThreadHandler = new Handler(context.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                ParkingFieldTask parkingFieldTask = (ParkingFieldTask)msg.obj;

                if (!parkingFieldTask.hasError()) {
                    List<ParkingField> parkingFields = parkingFieldTask.getParkingFields();
                    mGetParkingFieldCallback.onParkingFieldsLoaded(parkingFields);
                } else {
                    mGetParkingFieldCallback.onDataUnavailable(parkingFieldTask.getErrorMessage());
                }
            }
        };

        mDelayMillis = 0;
    }

    public void setGetParkingFieldCallback(GetParkingFieldCallback callback) {
        mGetParkingFieldCallback = callback;
    }

    public void startLoading() {
        ParkingFieldHandlerThread thread = new ParkingFieldHandlerThread(new ParkingFieldService(), mMainThreadHandler);
        thread.setDelayMillis(mDelayMillis);
        thread.start();
    }

    public void setDelayMillis(long delayMillis) {
        mDelayMillis = delayMillis;
    }
}
