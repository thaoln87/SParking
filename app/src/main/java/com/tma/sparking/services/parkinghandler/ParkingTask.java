package com.tma.sparking.services.parkinghandler;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.tma.sparking.models.ParkingField;
import com.tma.sparking.services.parkingfieldservice.ParkingFieldService;
import com.tma.sparking.services.provider.ParkingContract;
import com.tma.sparking.services.provider.ParkingFieldDataBuilder;

import java.util.List;

class ParkingTask implements Runnable {
    private ContentResolver mContentResolver;
    private ParkingFieldService mParkingFieldService;

    public ParkingTask(Context context, ParkingFieldService parkingFieldService) {
        mContentResolver = context.getContentResolver();
        mParkingFieldService = parkingFieldService;
    }

    public void run() {
        List<ParkingField> parkingFields = mParkingFieldService.findAll();
        Uri uri = ParkingContract.ParkingFieldEntry.CONTENT_URI;

        for (ParkingField parkingField : parkingFields) {
            if (parkingField != null) {
                String nameColumn = ParkingContract.ParkingFieldEntry.COLUMN_NAME_NAME;
                String selection = nameColumn + " = ?";
                String[] selectionArgs = {parkingField.getName()};
                Cursor cursor = mContentResolver.query(uri, null, selection, selectionArgs, null);
                ContentValues values = ParkingFieldDataBuilder.createContentValuesFromParkingField(parkingField);
                if (!cursor.moveToFirst()) {
                    mContentResolver.insert(uri, values);
                } else {
                    String whereClause = nameColumn + " = ?";
                    String[] whereArgs = {parkingField.getName()};
                    mContentResolver.update(uri, values, whereClause, whereArgs);
                }
            }
        }
    }
}
