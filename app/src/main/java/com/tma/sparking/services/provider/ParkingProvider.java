package com.tma.sparking.services.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tma.sparking.models.ParkingField;
import com.tma.sparking.services.ParkingFieldService;

/**
 * Content provider for parking data
 */
public class ParkingProvider extends ContentProvider {
    public static final String AUTHORITY = "com.tma.sparking.provider";

    private ParkingFieldService mParkingFieldService;

    @Override
    public boolean onCreate() {
        mParkingFieldService = new ParkingFieldService();

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        long channelId = Long.parseLong(selectionArgs[0]);
        int fieldId = Integer.parseInt(selectionArgs[1]);
        ParkingField parkingField = mParkingFieldService.findOne(channelId, fieldId);

        String[] columnNames = {
                ParkingContract.ParkingFieldEntry.COLUMN_NAME_ID,
                ParkingContract.ParkingFieldEntry.COLUMN_NAME_NAME,
                ParkingContract.ParkingFieldEntry.COLUMN_NAME_CHANNEL,
                ParkingContract.ParkingFieldEntry.COLUMN_NAME_LAST_ENTRY_ID,
                ParkingContract.ParkingFieldEntry.COLUMN_NAME_TOTAL_SLOT,
                ParkingContract.ParkingFieldEntry.COLUMN_NAME_EMPTY_SLOT
        };
        MatrixCursor cursor = new MatrixCursor(columnNames);
        Object[] columnValues = {
                parkingField.getId(),
                parkingField.getName(),
                parkingField.getChannel(),
                parkingField.getLastEntryId(),
                parkingField.getTotalSlot(),
                parkingField.getEmptySlot()
        };
        cursor.addRow(columnValues);

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}