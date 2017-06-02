package com.tma.sparking.services.parkinghandler;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.tma.sparking.services.parkingfieldservice.imp.ParkingFieldServiceImp;

public class ParkingHandlerService extends Service {
    private ParkingServiceHandlerThread mThread;
    private long mDelayMillis;

    @Override
    public void onCreate() {
        super.onCreate();

        mDelayMillis = 5000;

        ParkingTask parkingTask = new ParkingTask(this, new ParkingFieldServiceImp());
        mThread = new ParkingServiceHandlerThread(parkingTask);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mThread.setDelayMillis(mDelayMillis);
        mThread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mThread.quit();
    }
}
