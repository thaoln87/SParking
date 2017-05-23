package com.tma.sparking.services.provider;

/**
 * Created by pkimhuy on 5/23/2017.
 */

public final class ParkingContract {
    private ParkingContract() {}

    public final class ParkingField {
        public static final String TABLE_NAME = "parking_field";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_CHANNEL = "channel";
        public static final String COLUMN_NAME_LAST_ENTRY_ID = "last_entry_id";
        public static final String COLUMN_NAME_TOTAL_SLOT = "total_slot";
        public static final String COLUMN_NAME_EMPTY_SLOT = "empty_slot";
    }
}