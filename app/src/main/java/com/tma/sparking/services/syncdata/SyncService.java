package com.tma.sparking.services.syncdata;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.tma.sparking.services.http.ParkingFieldService;
import com.tma.sparking.services.http.RetrofitParkingFieldService;

/**
 * Created by pkimhuy on 5/23/2017.
 */
public class SyncService extends Service {
    private static SyncAdapter sSyncAdapter = null;
    private static final Object sSyncAdapterLock = new Object();

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                ParkingFieldService parkingFieldService = new RetrofitParkingFieldService();
                sSyncAdapter = new SyncAdapter(getApplicationContext(), true, parkingFieldService);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
