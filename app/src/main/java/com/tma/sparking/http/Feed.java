package com.tma.sparking.http;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by pkimhuy on 5/19/2017.
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
