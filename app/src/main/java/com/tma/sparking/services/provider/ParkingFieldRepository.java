package com.tma.sparking.services.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tma.sparking.models.ParkingField;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * CRUD repository for parking_field table
 */
public class ParkingFieldRepository {
    private SQLiteDatabase mReadableDatabase;
    private SQLiteDatabase mWritableDatabase;

    private String mParkingFieldTable = ParkingContract.ParkingFieldEntry.TABLE_NAME;

    /**
     * Construct the repository with readable and writable database object
     *
     * @param readableDatabase a SQLite readable database
     * @param writableDatabase a SQLite writable database
     */
    public ParkingFieldRepository(SQLiteDatabase readableDatabase, SQLiteDatabase writableDatabase) {
        mReadableDatabase = readableDatabase;
        mWritableDatabase = writableDatabase;
    }

    /**
     * Insert a parking field object into database table
     *
     * @param parkingField
     * @return primary key (_id) of inserted row
     */
    public long insert(ParkingField parkingField) {
        ContentValues values = ParkingFieldDataBuilder.createContentValuesFromParkingField(parkingField);

        return mWritableDatabase.insert(mParkingFieldTable, null, values);
    }

    /**
     * Insert a parking field object into database table
     *
     * @param values store a set of key-value pairs
     * @return primary key (_id) of inserted row
     */
    public long insert(ContentValues values) {
        return mWritableDatabase.insert(mParkingFieldTable, null, values);
    }

    /**
     * Find a parking field by primary key _id
     *
     * @param id primary key
     * @return parking field object
     */
    public ParkingField findOne(long id) {
        String selection = ParkingContract.ParkingFieldEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor cursor = mReadableDatabase.query(mParkingFieldTable, null, selection, selectionArgs, null, null, null);
        ParkingField parkingField = null;
        if (cursor.moveToFirst()) {
            parkingField = ParkingFieldDataBuilder.createFromCursor(cursor);
        }
        cursor.close();

        return parkingField;
    }

    /**
     * Execute a SQL query
     *
     * @param projection list of columns to return
     * @param selection similar to where clause
     * @param selectionArgs arguments to ? placeholder in selection string
     * @param sortOrder similar to order by
     * @return a cursor contain query result
     */
    public Cursor query(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return mReadableDatabase.query(mParkingFieldTable, projection, selection, selectionArgs, null, null, sortOrder);
    }

    /**
     * Execute query on one row
     *
     * @param id primary key of row
     * @param projection list of columns to return
     * @param selection similar to where clause
     * @param selectionArgs arguments to ? placeholder in selection string
     * @param sortOrder similar to order by
     * @return a cursor contain query result
     */
    public Cursor query(long id, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String entrySelection = selection + " AND " + ParkingContract.ParkingFieldEntry._ID + " = ?";

        ArrayList<String> args = new ArrayList<>(Arrays.asList(selectionArgs));
        args.add(String.valueOf(id));
        String[] entrySelectionArgs = new String[args.size()];
        args.toArray(entrySelectionArgs);

        return mReadableDatabase.query(mParkingFieldTable, projection, entrySelection, entrySelectionArgs, null, null, sortOrder);
    }

    /**
     * Update a row
     *
     * @param values a set of key-value pairs
     * @param selection similar to where clause
     * @param selectionArgs arguments to ? placeholder in selection string
     * @return
     */
    public int update(ContentValues values, String selection, String[] selectionArgs) {
        return mWritableDatabase.update(mParkingFieldTable, values, selection, selectionArgs);
    }

    public int update(long id, ContentValues values, String selection, String[] selectionArgs) {
        String entrySelection = selection + " AND " + ParkingContract.ParkingFieldEntry._ID + " = ?";

        ArrayList<String> args = new ArrayList<>(Arrays.asList(selectionArgs));
        args.add(String.valueOf(id));
        String[] entrySelectionArgs = new String[args.size()];

        return mWritableDatabase.update(mParkingFieldTable, values, entrySelection, entrySelectionArgs);
    }

    public int delete(long id) {
        String whereClause = ParkingContract.ParkingFieldEntry._ID + " = ?";
        String[] whereArgs = { String.valueOf(id) };
        return mWritableDatabase.delete(mParkingFieldTable, whereClause, whereArgs);
    }

    public int delete(String selection, String[] selectionArgs) {
        return mWritableDatabase.delete(mParkingFieldTable, selection, selectionArgs);
    }

    public int delete(long id, String selection, String[] selectionArgs) {
        String entrySelection = selection + " AND " + ParkingContract.ParkingFieldEntry._ID + " = ?";

        ArrayList<String> args = new ArrayList<>(Arrays.asList(selectionArgs));
        args.add(String.valueOf(id));
        String[] entrySelectionArgs = new String[args.size()];

        return mWritableDatabase.delete(mParkingFieldTable, entrySelection, entrySelectionArgs);
    }
}
