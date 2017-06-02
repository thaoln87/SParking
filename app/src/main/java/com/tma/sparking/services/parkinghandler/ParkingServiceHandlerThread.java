package com.tma.sparking.services.parkinghandler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;

/**
 * Background thread responsible for fetch data from server
 */
class ParkingServiceHandlerThread extends HandlerThread {
    private static final String THREAD_NAME = "parking_field_handler_thread";

    private Handler mHandler;
    private long mDelayMillis;
    private ParkingTask mParkingTask;

    public ParkingServiceHandlerThread(ParkingTask parkingTask) {
        super(THREAD_NAME, Process.THREAD_PRIORITY_BACKGROUND);

        mParkingTask = parkingTask;

        mDelayMillis = 0;
    }

    public void setDelayMillis(long delayMillis) {
        mDelayMillis = delayMillis;
    }

    @Override
    protected void onLooperPrepared() {
        mHandler = new Handler(getLooper());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mParkingTask.execute();

                if (isScheduled()) {
                    mHandler.postDelayed(this, mDelayMillis);
                }
            }
        }, 0);
    }

    private boolean isScheduled() {
        return mDelayMillis != 0;
    }
}
