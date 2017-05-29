package com.tma.sparking.services.http;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Each field have many feed, each feed represent a parking
 */
class Feed {
    @SerializedName("entry_id")
    private long mEntryId;

    @SerializedName("created_at")
    private Date mCreatedAt;

    private String mFieldStatus;

    long getEntryId() {
        return mEntryId;
    }

    void setEntryId(long entryId) {
        mEntryId = entryId;
    }

    Date getCreatedAt() {
        return mCreatedAt;
    }

    void setCreatedAt(Date createdAt) {
        mCreatedAt = createdAt;
    }

    String getFieldStatus() {
        return mFieldStatus;
    }

    void setFieldStatus(String fieldStatus) {
        mFieldStatus = fieldStatus;
    }
}
