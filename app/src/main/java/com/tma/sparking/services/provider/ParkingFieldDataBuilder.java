package com.tma.sparking.services.provider;

import android.content.ContentValues;
import android.database.Cursor;

import com.tma.sparking.models.ParkingField;

public class ParkingFieldDataBuilder {
    public static ParkingField createFromCursor(Cursor cursor) {
        ParkingField parkingField = new ParkingField();

        String name = cursor.getString(cursor.getColumnIndexOrThrow(ParkingContract.ParkingFieldEntry.COLUMN_NAME_NAME));
        parkingField.setName(name);

        int totalSlot = cursor.getInt(cursor.getColumnIndexOrThrow(ParkingContract.ParkingFieldEntry.COLUMN_NAME_TOTAL_SLOT));
        parkingField.setTotalSlot(totalSlot);

        int emptySlot = cursor.getInt(cursor.getColumnIndexOrThrow(ParkingContract.ParkingFieldEntry.COLUMN_NAME_EMPTY_SLOT));
        parkingField.setEmptySlot(emptySlot);

        double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(ParkingContract.ParkingFieldEntry.COLUMN_NAME_LATITUDE));
        parkingField.setLatitude(latitude);

        double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(ParkingContract.ParkingFieldEntry.COLUMN_NAME_LONGITUDE));
        parkingField.setLongitude(longitude);

        return parkingField;
    }

    public static ContentValues createContentValuesFromParkingField(ParkingField parkingField) {
        ContentValues values = new ContentValues();

        values.put(ParkingContract.ParkingFieldEntry.COLUMN_NAME_NAME, parkingField.getName());
        values.put(ParkingContract.ParkingFieldEntry.COLUMN_NAME_EMPTY_SLOT, parkingField.getEmptySlot());
        values.put(ParkingContract.ParkingFieldEntry.COLUMN_NAME_TOTAL_SLOT, parkingField.getTotalSlot());
        values.put(ParkingContract.ParkingFieldEntry.COLUMN_NAME_LATITUDE, parkingField.getLatitude());
        values.put(ParkingContract.ParkingFieldEntry.COLUMN_NAME_LONGITUDE, parkingField.getLongitude());

        return values;
    }
}
