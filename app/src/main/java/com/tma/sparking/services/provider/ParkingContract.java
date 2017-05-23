package com.tma.sparking.services.provider;

import android.provider.BaseColumns;

/**
 * Define name for URL, table, column,...
 */
public class ParkingContract {
    private ParkingContract() {}

    public static class ParkingField implements BaseColumns {
        public static final String TABLE_NAME = "parking_field";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_CHANNEL_ID = "channel_id";
        public static final String COLUMN_NAME_LAST_ENTRY_ID = "last_entry_id";
        public static final String COLUMN_NAME_TOTAL_SLOT = "total_slot";
        public static final String COLUMN_NAME_EMPTY_SLOT = "empty_slot";
    }

    public static final String SQL_CREATE_PARKING_FIELD = "CREATE TABLE " + ParkingField.TABLE_NAME + " (" +
            ParkingField._ID + " INTEGER PRIMARY KEY, " +
            ParkingField.COLUMN_NAME_NAME + " TEXT, " +
            ParkingField.COLUMN_NAME_CHANNEL_ID + " INTEGER, " +
            ParkingField.COLUMN_NAME_LAST_ENTRY_ID + " INTEGER, " +
            ParkingField.COLUMN_NAME_TOTAL_SLOT + " INTEGER, " +
            ParkingField.COLUMN_NAME_EMPTY_SLOT + " INTEGER, " +
            "FOREIGN KEY(" + ParkingField.COLUMN_NAME_CHANNEL_ID + " REFERENCES " + Channel.TABLE_NAME + "(" + Channel._ID + ")";

    public static final String SQL_DELETE_PARKING_FIELD = "DROP TABLE IF EXISTS " + ParkingField.TABLE_NAME;

    public static class Channel implements BaseColumns {
        public static final String TABLE_NAME = "channel";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_UPDATED_AT = "updated_at";
        public static final String COLUMN_NAME_CREATED_AT = "created_at";
        public static final String COLUMN_NAME_LAST_ENTRY_ID = "last_entry_id";
    }

    public static final String SQL_CREATE_CHANNEL = "CREATE TABLE " + Channel.TABLE_NAME + " (" +
            Channel._ID + " INTEGER PRIMARY KEY, " +
            Channel.COLUMN_NAME_NAME + " TEXT, " +
            Channel.COLUMN_NAME_DESCRIPTION + " TEXT, " +
            Channel.COLUMN_NAME_LATITUDE + " REAL, " +
            Channel.COLUMN_NAME_LONGITUDE + " REAL, " +
            Channel.COLUMN_NAME_UPDATED_AT + " TEXT, " +
            Channel.COLUMN_NAME_CREATED_AT + " TEXT, " +
            Channel.COLUMN_NAME_LAST_ENTRY_ID + " INTEGER)";

    public static final String SQL_DELETE_CHANNEL = "DROP TABLE IF EXISTS " + Channel.TABLE_NAME;

    public static class Feed implements BaseColumns {
        public static final String TABLE_NAME = "feed";
        public static final String COLUMN_NAME_CREATED_AT = "created_at";
        public static final String COLUMN_NAME_CHANNEL_ID = "channel_id";
        public static final String COLUMN_NAME_FIELD_STATUS = "field_status";
    }

    public static final String SQL_DELETE_FEED = "DROP TABLE IF EXISTS " + Feed.TABLE_NAME;

    public static final String SQL_CREATE_FEED = "CREATE TABLE " + Feed.TABLE_NAME + " (" +
            Feed._ID + " INTEGER PRIMARY KEY, " +
            Feed.COLUMN_NAME_CREATED_AT + " TEXT, " +
            Feed.COLUMN_NAME_CHANNEL_ID + " INTEGER, " +
            Feed.COLUMN_NAME_FIELD_STATUS + " TEXT, " +
            "FOREIGN KEY(" + Feed.COLUMN_NAME_CHANNEL_ID + " REFERENCES " + Channel.TABLE_NAME + "(" + Channel._ID + ")";
}