package com.tma.sparking.services.syncdata;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.tma.sparking.models.ParkingField;
import com.tma.sparking.services.provider.ParkingContract;
import com.tma.sparking.services.provider.ParkingFieldDataBuilder;
import com.tma.sparking.services.provider.ParkingProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Define convenient methods to work with sync data framework
 */
public class SyncDataManager extends Observable {
    private Context mContext;
    private List<ParkingField> mParkingFieldList;

    public SyncDataManager(Context context) {
        mContext = context;
    }

    /**
     * Watch for parking field data available or not and notify all registered observer
     *
     * @param syncImmediately if true, call ContentResolver.requestSync() immediately,
     *                        otherwise we must wait for a period of time before this method is being called
     */
    public void notifyDataAvailable(boolean syncImmediately) {
        Uri uri = ParkingContract.ParkingFieldEntry.CONTENT_URI;
        final ContentResolver contentResolver = mContext.getContentResolver();

        contentResolver.registerContentObserver(uri, true, new ContentObserver(new Handler(Looper.getMainLooper())) {
            @Override
            public void onChange(boolean selfChange) {

            }

            @Override
            public void onChange(boolean selfChange, Uri uri) {
                Cursor cursor = contentResolver.query(uri, null, null, null, null);
                List<ParkingField> parkingFields = new ArrayList<ParkingField>();
                while (cursor.moveToNext()) {
                    ParkingField parkingField = ParkingFieldDataBuilder.createFromCursor(cursor);
                    parkingFields.add(parkingField);
                }
                cursor.close();

                mParkingFieldList = parkingFields;

                setChanged();
                notifyObservers();
            }
        });

        if (syncImmediately) {
            requestSync();
        }
    }

    public List<ParkingField> getParkingFieldList() {
        return mParkingFieldList;
    }

    public void startPollingService() {
        Intent intent = new Intent(mContext, AlarmService.class);
        mContext.startService(intent);
    }

    public void requestSync() {
        Account account = SyncUtil.createSyncAccount(mContext);
        Bundle extras = Bundle.EMPTY;
        ContentResolver.setSyncAutomatically(account, ParkingProvider.AUTHORITY, true);
        ContentResolver.setMasterSyncAutomatically(true);
        ContentResolver.requestSync(account, ParkingProvider.AUTHORITY, extras);
    }
}
