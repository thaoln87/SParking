package com.tma.sparking.services.http;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Define channel data
 */
public class Channel {
    @SerializedName("id")
    private Long mId;

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

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        mCreatedAt = createdAt;
    }

    public Date getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public Long getLastEntryId() {
        return mLastEntryId;
    }

    public void setLastEntryId(Long lastEntryId) {
        mLastEntryId = lastEntryId;
    }

    public List<Field> getFields() {
        return mFields;
    }

    public void setFields(List<Field> fields) {
        mFields = fields;
    }
}
