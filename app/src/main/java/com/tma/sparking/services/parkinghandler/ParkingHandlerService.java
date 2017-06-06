package com.tma.sparking.services.parkinghandler;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.tma.sparking.services.parkingfieldservice.ParkingFieldService;
import com.tma.sparking.services.parkingfieldservice.imp.ParkingFieldServiceImp;

public class ParkingHandlerService extends Service {
    private ParkingServiceHandlerThread mThread;
    private long mDelayMillis;
    private Runnable mParkingTask;

    @Override
    public void onCreate() {
        super.onCreate();

        mDelayMillis = 20000;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mParkingTask == null) {
            ParkingFieldService parkingFieldService = new ParkingFieldServiceImp();
            mParkingTask = new ParkingTask(this, parkingFieldService);
        }

        mThread = new ParkingServiceHandlerThread(mParkingTask);
        mThread.setDelayMillis(mDelayMillis);
        mThread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mThread.quit();
    }

    public void setParkingTask(Runnable parkingTask) {
        mParkingTask = parkingTask;
    }

    public void setDelayMillis(long delayMillis) {
        mDelayMillis = delayMillis;
    }
}
