package com.tma.sparking.services.handler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.tma.sparking.models.ParkingField;
import com.tma.sparking.services.ParkingFieldService;

import java.util.ArrayList;
import java.util.List;

/**
 * Background thread responsible for fetch data from server
 */
class ParkingFieldHandlerThread extends HandlerThread {
    private static final String THREAD_NAME = "parking_field_handler_thread";

    private Handler mHandler;
    private Handler mMainThreadHandler;
    private ParkingFieldService mParkingFieldService;
    private GetParkingFieldTask mGetParkingFieldTask;
    private long mDelayMillis;

    ParkingFieldHandlerThread(ParkingFieldService parkingFieldService, Handler mainThreadHandler) {
        super(THREAD_NAME);

        mParkingFieldService = parkingFieldService;
        mMainThreadHandler = mainThreadHandler;

        mDelayMillis = 0;
    }

    @Override
    protected void onLooperPrepared() {
        mHandler = new Handler(getLooper());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<ParkingField> parkingFields = new ArrayList<>();
                long channelId = mGetParkingFieldTask.getChannelId();
                for (int i = 1; i <= 8; i++) {
                    ParkingField parkingField = mParkingFieldService.findOne(channelId, i);

                    if (parkingField != null) {
                        parkingFields.add(parkingField);
                    } else {
                        mGetParkingFieldTask.setError("ParkingField with id " + i + ", in channel " + channelId + " is unavailable!");
                        break;
                    }
                }

                if (!mGetParkingFieldTask.hasError()) {
                    mGetParkingFieldTask.setParkingFields(parkingFields);
                }

                mMainThreadHandler.sendEmptyMessage(0);

                if (isScheduled()) {
                    mHandler.postDelayed(this, mDelayMillis);
                }
            }
        }, 0);
    }

    void setDelayMillis(long delayMillis) {
        mDelayMillis = delayMillis;
    }

    void setGetParkingFieldTask(GetParkingFieldTask getParkingFieldTask) {
        mGetParkingFieldTask = getParkingFieldTask;
    }

    private boolean isScheduled() {
        return mDelayMillis != 0;
    }
}
