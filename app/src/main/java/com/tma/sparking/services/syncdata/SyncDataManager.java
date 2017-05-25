package com.tma.sparking.services.syncdata;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.tma.sparking.models.ParkingField;
import com.tma.sparking.services.provider.ParkingContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class SyncDataManager extends Observable {
    private Context mContext;
    private List<ParkingField> mParkingFieldList;

    public SyncDataManager(Context context) {
        mContext = context;

        notifyDataAvailable();
    }

    public void notifyDataAvailable() {
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
                    ParkingField parkingField = getParkingFieldFromCursor(cursor);
                    parkingFields.add(parkingField);
                }
                cursor.close();

                mParkingFieldList = parkingFields;

                setChanged();
                notifyObservers();
            }
        });
    }

    public List<ParkingField> getParkingFieldList() {
        return mParkingFieldList;
    }

    public void startPollingService() {
        Intent intent = new Intent(mContext, AlarmService.class);
        mContext.startService(intent);
    }

    private ParkingField getParkingFieldFromCursor(Cursor cursor) {
        ParkingField parkingField = new ParkingField();

        long id = cursor.getLong(cursor.getColumnIndexOrThrow(ParkingContract.ParkingFieldEntry._ID));
        parkingField.setId(id);

        int parkingFieldNumber = cursor.getInt(cursor.getColumnIndexOrThrow(ParkingContract.ParkingFieldEntry.COLUMN_NAME_PARKING_FIELD_NUMBER));
        parkingField.setId(parkingFieldNumber);

        String name = cursor.getString(cursor.getColumnIndexOrThrow(ParkingContract.ParkingFieldEntry.COLUMN_NAME_NAME));
        parkingField.setName(name);

        int totalSlot = cursor.getInt(cursor.getColumnIndexOrThrow(ParkingContract.ParkingFieldEntry.COLUMN_NAME_TOTAL_SLOT));
        parkingField.setTotalSlot(totalSlot);

        int emptySlot = cursor.getInt(cursor.getColumnIndexOrThrow(ParkingContract.ParkingFieldEntry.COLUMN_NAME_EMPTY_SLOT));
        parkingField.setEmptySlot(emptySlot);

        long lastEntryId = cursor.getLong(cursor.getColumnIndexOrThrow(ParkingContract.ParkingFieldEntry.COLUMN_NAME_LAST_ENTRY_ID));
        parkingField.setLastEntryId(lastEntryId);

        double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(ParkingContract.ParkingFieldEntry.COLUMN_NAME_LATITUDE));
        parkingField.setLatitude(latitude);

        double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(ParkingContract.ParkingFieldEntry.COLUMN_NAME_LONGITUDE));
        parkingField.setLongitude(longitude);

        long channelId = cursor.getLong(cursor.getColumnIndexOrThrow(ParkingContract.ParkingFieldEntry.COLUMN_NAME_CHANNEL_ID));
        parkingField.setChannelId(channelId);

        String channelName = cursor.getString(cursor.getColumnIndexOrThrow(ParkingContract.ParkingFieldEntry.COLUMN_NAME_CHANNEL_NAME));
        parkingField.setChannelName(channelName);

        return parkingField;
    }
}
