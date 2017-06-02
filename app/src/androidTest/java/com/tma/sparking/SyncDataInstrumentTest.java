package com.tma.sparking;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.tma.sparking.services.provider.ParkingContract;
import com.tma.sparking.services.provider.ParkingProvider;
import com.tma.sparking.services.syncdata.SyncAdapter;
import com.tma.sparking.services.syncdata.SyncUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SyncDataInstrumentTest {
    @Test
    public void testSyncData() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        Account account = SyncUtil.createSyncAccount(appContext);

        Bundle extras = new Bundle();
        extras.putLong(SyncAdapter.KEY_CHANNEL_ID, 270768);
        extras.putInt(SyncAdapter.KEY_FIELD_ID, 2);
        ContentResolver.setSyncAutomatically(account, ParkingProvider.AUTHORITY, true);
        ContentResolver.setMasterSyncAutomatically(true);
        ContentResolver.requestSync(account, ParkingProvider.AUTHORITY, extras);

        ContentResolver contentResolver = appContext.getContentResolver();
        Uri uri = ParkingContract.ParkingFieldEntry.CONTENT_URI;
        String channelIdColumn = ParkingContract.ParkingFieldEntry.COLUMN_NAME_CHANNEL_ID;
        String parkingFieldNumberColumn = ParkingContract.ParkingFieldEntry.COLUMN_NAME_PARKING_FIELD_NUMBER;
        String selection = channelIdColumn + " = ? AND " + parkingFieldNumberColumn + " = ?";
        String[] selectionArgs = { String.valueOf(270768), String.valueOf(2) };
        Cursor cursor = contentResolver.query(uri, null, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(ParkingContract.ParkingFieldEntry.COLUMN_NAME_CHANNEL_ID));
            Log.d("abc", String.valueOf(id));
        } else {
            Log.d("abc", "dd");
        }
    }
}
