package com.tma.sparking.services.http;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Define parking channel data
 */
class Channel {
    @SerializedName("id")
    private long mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("latitude")
    private double mLatitude;

    @SerializedName("longitude")
    private double mLongitude;

    @SerializedName("created_at")
    private Date mCreatedAt;

    @SerializedName("updated_at")
    private Date mUpdatedAt;

    @SerializedName("last_entry_id")
    private long mLastEntryId;

    private List<Field> mFields;

    long getId() {
        return mId;
    }

    void setId(long id) {
        mId = id;
    }

    String getName() {
        return mName;
    }

    void setName(String name) {
        mName = name;
    }

    String getDescription() {
        return mDescription;
    }

    void setDescription(String description) {
        mDescription = description;
    }

    double getLatitude() {
        return mLatitude;
    }

    void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    double getLongitude() {
        return mLongitude;
    }

    void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    Date getCreatedAt() {
        return mCreatedAt;
    }

    void setCreatedAt(Date createdAt) {
        mCreatedAt = createdAt;
    }

    Date getUpdatedAt() {
        return mUpdatedAt;
    }

    void setUpdatedAt(Date updatedAt) {
        mUpdatedAt = updatedAt;
    }

    Long getLastEntryId() {
        return mLastEntryId;
    }

    void setLastEntryId(Long lastEntryId) {
        mLastEntryId = lastEntryId;
    }

    List<Field> getFields() {
        return mFields;
    }

    void setFields(List<Field> fields) {
        mFields = fields;
    }
}
