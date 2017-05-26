package com.tma.sparking.services.provider;

import android.content.ContentValues;
import android.database.Cursor;

import com.tma.sparking.models.ParkingField;

public class ParkingFieldDataBuilder {
    public static ParkingField createFromCursor(Cursor cursor) {
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

    public static ContentValues createContentValuesFromParkingField(ParkingField parkingField) {
        ContentValues values = new ContentValues();

        values.put(ParkingContract.ParkingFieldEntry.COLUMN_NAME_PARKING_FIELD_NUMBER, parkingField.getNumber());
        values.put(ParkingContract.ParkingFieldEntry.COLUMN_NAME_NAME, parkingField.getName());
        values.put(ParkingContract.ParkingFieldEntry.COLUMN_NAME_EMPTY_SLOT, parkingField.getEmptySlot());
        values.put(ParkingContract.ParkingFieldEntry.COLUMN_NAME_TOTAL_SLOT, parkingField.getTotalSlot());
        values.put(ParkingContract.ParkingFieldEntry.COLUMN_NAME_LAST_ENTRY_ID, parkingField.getLastEntryId());
        values.put(ParkingContract.ParkingFieldEntry.COLUMN_NAME_LATITUDE, parkingField.getLatitude());
        values.put(ParkingContract.ParkingFieldEntry.COLUMN_NAME_LONGITUDE, parkingField.getLongitude());
        values.put(ParkingContract.ParkingFieldEntry.COLUMN_NAME_CHANNEL_ID, parkingField.getChannelId());
        values.put(ParkingContract.ParkingFieldEntry.COLUMN_NAME_CHANNEL_NAME, parkingField.getChannelName());

        return values;
    }
}
