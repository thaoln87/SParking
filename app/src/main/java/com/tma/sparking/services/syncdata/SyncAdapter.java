package com.tma.sparking.services.syncdata;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.tma.sparking.models.ParkingField;
import com.tma.sparking.services.http.ParkingFieldService;
import com.tma.sparking.services.http.RetrofitParkingFieldService;
import com.tma.sparking.services.provider.ParkingContract;
import com.tma.sparking.services.provider.ParkingFieldDataBuilder;

/**
 * Created by pkimhuy on 5/23/2017.
 */

public class SyncAdapter extends AbstractThreadedSyncAdapter {
    public static final String KEY_CHANNEL_ID = "key_channel_id";
    public static final String KEY_FIELD_ID = "key_field_id";

    private ParkingFieldService mParkingFieldService;
    private ContentResolver mContentResolver;

    public SyncAdapter(Context context, boolean autoInitialize, ParkingFieldService parkingFieldService) {
        super(context, autoInitialize);

        mParkingFieldService = parkingFieldService;
        mContentResolver = getContext().getContentResolver();
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s,
                              ContentProviderClient contentProviderClient, SyncResult syncResult) {
        long channelId = bundle.getLong(KEY_CHANNEL_ID);
        Uri uri = ParkingContract.ParkingFieldEntry.CONTENT_URI;

        for (int i = 1; i <= 8; i++) {
            ParkingField parkingField = mParkingFieldService.findOne(channelId, i);

            if (parkingField != null) {
                String channelIdColumn = ParkingContract.ParkingFieldEntry.COLUMN_NAME_CHANNEL_ID;
                String parkingFieldNumberColumn = ParkingContract.ParkingFieldEntry.COLUMN_NAME_PARKING_FIELD_NUMBER;
                String selection = channelIdColumn + " = ? AND " + parkingFieldNumberColumn + " = ?";
                String[] selectionArgs = { String.valueOf(parkingField.getChannelId()), String.valueOf(parkingField.getNumber()) };
                Cursor cursor = mContentResolver.query(uri, null, selection, selectionArgs, null);
                ContentValues values = ParkingFieldDataBuilder.createContentValuesFromParkingField(parkingField);
                if (!cursor.moveToFirst()) {
                    mContentResolver.insert(uri, values);
                } else {
                    String whereClause = channelIdColumn + " = ? AND " + parkingFieldNumberColumn + " = ?";
                    String[] whereArgs = { String.valueOf(parkingField.getChannelId()), String.valueOf(parkingField.getNumber()) };
                    mContentResolver.update(uri, values, whereClause, whereArgs);
                }
            }
        }
        getContext().getContentResolver().notifyChange(uri, null, false);
    }
}
