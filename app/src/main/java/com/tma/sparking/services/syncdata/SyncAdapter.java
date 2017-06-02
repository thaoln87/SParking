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
import com.tma.sparking.services.parkingfieldservice.ParkingFieldService;
import com.tma.sparking.services.parkingfieldservice.imp.ParkingFieldServiceImp;
import com.tma.sparking.services.provider.ParkingContract;
import com.tma.sparking.services.provider.ParkingFieldDataBuilder;

import java.util.List;

/**
 * Created by pkimhuy on 5/23/2017.
 */

public class SyncAdapter extends AbstractThreadedSyncAdapter {
    public static final String KEY_CHANNEL_ID = "key_channel_id";
    public static final String KEY_FIELD_ID = "key_field_id";

    private ParkingFieldService mParkingFieldService;
    private ContentResolver mContentResolver;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);

        mParkingFieldService = new ParkingFieldServiceImp();
        mContentResolver = getContext().getContentResolver();
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s,
                              ContentProviderClient contentProviderClient, SyncResult syncResult) {
        Uri uri = ParkingContract.ParkingFieldEntry.CONTENT_URI;

        List<ParkingField> parkingFields = mParkingFieldService.findAll();

        for (ParkingField parkingField : parkingFields) {
            if (parkingField != null) {
                String nameColumn = ParkingContract.ParkingFieldEntry.COLUMN_NAME_NAME;
                String selection = nameColumn + " = ?";
                String[] selectionArgs = { parkingField.getName() };
                Cursor cursor = mContentResolver.query(uri, null, selection, selectionArgs, null);
                ContentValues values = ParkingFieldDataBuilder.createContentValuesFromParkingField(parkingField);
                if (!cursor.moveToFirst()) {
                    mContentResolver.insert(uri, values);
                } else {
                    String whereClause = nameColumn + " = ?";
                    String[] whereArgs = { parkingField.getName() };
                    mContentResolver.update(uri, values, whereClause, whereArgs);
                }
                cursor.close();
            }
        }

        getContext().getContentResolver().notifyChange(uri, null, false);
    }
}
