package com.tma.sparking.services.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tma.sparking.models.ParkingField;
import com.tma.sparking.services.ParkingFieldService;

import java.util.Arrays;

/**
 * Content provider for parking data
 */
public class ParkingProvider extends ContentProvider {
    public static final String AUTHORITY = ParkingContract.CONTENT_AUTHORITY;

    // URI id for route: /parking_field_entries
    public static final int ROUTE_PARKING_FIELD_ENTRIES = 1;

    // URI id for route: /parking_field_entries/{id}
    public static final int ROUTE_PARING_FIELD_ENTRIES_ID = 2;

    // Uri matcher decode incoming URIs
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(AUTHORITY, "parking_field_entries", ROUTE_PARKING_FIELD_ENTRIES);
        sUriMatcher.addURI(AUTHORITY, "parking_field_entries/*", ROUTE_PARING_FIELD_ENTRIES_ID);
    }

    private ParkingFieldRepository mParkingFieldRepository;

    @Override
    public boolean onCreate() {
        ParkingDbHelper dbHelper = new ParkingDbHelper(getContext());
        mParkingFieldRepository = new ParkingFieldRepository(dbHelper.getReadableDatabase(), dbHelper.getWritableDatabase());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int uriMatch = sUriMatcher.match(uri);
        Cursor cursor;
        switch (uriMatch) {
            case ROUTE_PARKING_FIELD_ENTRIES:
                cursor = mParkingFieldRepository.query(projection, selection, selectionArgs, sortOrder);
                break;
            case ROUTE_PARING_FIELD_ENTRIES_ID:
                long id = Long.parseLong(uri.getLastPathSegment());
                cursor = mParkingFieldRepository.query(id, projection, selection, selectionArgs, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int uriMatch = sUriMatcher.match(uri);
        Uri result;
        switch (uriMatch) {
            case ROUTE_PARKING_FIELD_ENTRIES:
                long id = mParkingFieldRepository.insert(values);
                result = Uri.parse(ParkingContract.ParkingFieldEntry.CONTENT_URI + "/" + id);
                break;
            case ROUTE_PARING_FIELD_ENTRIES_ID:
                throw new UnsupportedOperationException("Insert is not supported on uri " + uri);
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }

        return result;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriMatch = sUriMatcher.match(uri);
        int count;
        switch (uriMatch) {
            case ROUTE_PARKING_FIELD_ENTRIES:
                count = mParkingFieldRepository.update(values, selection, selectionArgs);
                break;
            case ROUTE_PARING_FIELD_ENTRIES_ID:
                long id = Long.parseLong(uri.getLastPathSegment());
                count = mParkingFieldRepository.update(id, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }
        return count;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriMatch = sUriMatcher.match(uri);
        int count;
        switch (uriMatch) {
            case ROUTE_PARKING_FIELD_ENTRIES:
                count = mParkingFieldRepository.delete(selection, selectionArgs);
                break;
            case ROUTE_PARING_FIELD_ENTRIES_ID:
                long id = Long.parseLong(uri.getLastPathSegment());
                count = mParkingFieldRepository.delete(id, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }
        return count;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ROUTE_PARKING_FIELD_ENTRIES:
                return ParkingContract.ParkingFieldEntry.CONTENT_TYPE;
            case ROUTE_PARING_FIELD_ENTRIES_ID:
                return ParkingContract.ParkingFieldEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    private void notifyContentResolver(Cursor cursor, Uri uri) {
        Context context = getContext();

        if (cursor != null) {
            cursor.setNotificationUri(context.getContentResolver(), uri);
        } else {
            context.getContentResolver().notifyChange(uri, null, false);
        }
    }
}