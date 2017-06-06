package com.tma.sparking.services.parkinghandler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;

/**
 * Background thread responsible for fetch data from server
 */
class ParkingServiceHandlerThread extends HandlerThread {
    private static final String THREAD_NAME = "parking_field_handler_thread";

    private Runnable mTask;
    private Handler mHandler;
    private long mDelayMillis;

    public ParkingServiceHandlerThread(Runnable task) {
        super(THREAD_NAME, Process.THREAD_PRIORITY_BACKGROUND);

        mTask = task;
        mDelayMillis = 0;
    }

    public void setDelayMillis(long delayMillis) {
        mDelayMillis = delayMillis;
    }

    @Override
    protected void onLooperPrepared() {
        mHandler = new Handler(getLooper());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mTask.run();

                if (isScheduled()) {
                    mHandler.postDelayed(this, mDelayMillis);
                }
            }
        };

        mHandler.postDelayed(runnable, 0);
    }

    private boolean isScheduled() {
        return mDelayMillis != 0;
    }
}
