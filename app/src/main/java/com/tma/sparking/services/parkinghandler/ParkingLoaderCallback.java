package com.tma.sparking.services.parkinghandler;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.tma.sparking.models.ParkingField;
import com.tma.sparking.services.provider.ParkingContract;
import com.tma.sparking.services.provider.ParkingFieldDataBuilder;

import java.util.ArrayList;
import java.util.List;

public class ParkingLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {
    private Context mContext;
    private ParkingDataCallback mParkingDataCallback;

    public ParkingLoaderCallback(Context context, ParkingDataCallback parkingDataCallback) {
        mContext = context;
        mParkingDataCallback = parkingDataCallback;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = ParkingContract.ParkingFieldEntry.CONTENT_URI;
        CursorLoader cursorLoader = new CursorLoader(mContext, uri, null, null, null, null);
        cursorLoader.setUpdateThrottle(5000);
        return cursorLoader;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        loader.reset();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        List<ParkingField> parkingFields = new ArrayList<>();

        while (cursor.moveToNext()) {
            ParkingField parkingField = ParkingFieldDataBuilder.createFromCursor(cursor);
            parkingFields.add(parkingField);
        }

        if (parkingFields.size() > 0) {
            mParkingDataCallback.onParkingFieldsLoaded(parkingFields);
        }
    }
}
