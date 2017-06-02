package com.tma.sparking.services.parkingfieldservice;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Each field have many feed, each feed represent a parking
 */
public class Feed {
    @SerializedName("entry_id")
    private long mEntryId;

    @SerializedName("created_at")
    private Date mCreatedAt;

    private String mFieldStatus;

    public long getEntryId() {
        return mEntryId;
    }

    public void setEntryId(long entryId) {
        mEntryId = entryId;
    }

    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        mCreatedAt = createdAt;
    }

    public String getFieldStatus() {
        return mFieldStatus;
    }

    public void setFieldStatus(String fieldStatus) {
        mFieldStatus = fieldStatus;
    }
}
