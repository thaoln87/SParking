package com.tma.sparking.services.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Define name for URL, table, column,...
 */
public class ParkingContract {
    private ParkingContract() {}

    public static final String CONTENT_AUTHORITY = "com.tma.sparking.provider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_PARKING_FIELD_ENTRIES = "parking_field_entries";

    public static class ParkingFieldEntry implements BaseColumns {
        // MIME type for list of entries
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.sparking.parking_field_entries";

        // MIME type for individual entry
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.sparking.parking_field_entry";

        // Full URI for entry resources
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PARKING_FIELD_ENTRIES).build();

        public static final String TABLE_NAME = "parking_field";
        public static final String COLUMN_NAME_PARKING_FIELD_NUMBER = "parking_field_number";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_LAST_ENTRY_ID = "last_entry_id";
        public static final String COLUMN_NAME_TOTAL_SLOT = "total_slot";
        public static final String COLUMN_NAME_EMPTY_SLOT = "empty_slot";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_CHANNEL_ID = "channel_id";
        public static final String COLUMN_NAME_CHANNEL_NAME = "channel_name";
    }

    public static final String SQL_CREATE_PARKING_FIELD = "CREATE TABLE " + ParkingFieldEntry.TABLE_NAME + " (" +
            ParkingFieldEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ParkingFieldEntry.COLUMN_NAME_PARKING_FIELD_NUMBER + " INTEGER, " +
            ParkingFieldEntry.COLUMN_NAME_NAME + " TEXT, " +
            ParkingFieldEntry.COLUMN_NAME_LAST_ENTRY_ID + " INTEGER, " +
            ParkingFieldEntry.COLUMN_NAME_TOTAL_SLOT + " INTEGER, " +
            ParkingFieldEntry.COLUMN_NAME_EMPTY_SLOT + " INTEGER, " +
            ParkingFieldEntry.COLUMN_NAME_LATITUDE + " REAL, " +
            ParkingFieldEntry.COLUMN_NAME_LONGITUDE + " REAL, " +
            ParkingFieldEntry.COLUMN_NAME_CHANNEL_ID + " INTEGER, " +
            ParkingFieldEntry.COLUMN_NAME_CHANNEL_NAME + " TEXT)";

    public static final String SQL_DELETE_PARKING_FIELD = "DROP TABLE IF EXISTS " + ParkingFieldEntry.TABLE_NAME;
}