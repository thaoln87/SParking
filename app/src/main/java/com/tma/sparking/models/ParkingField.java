package com.tma.sparking.models;

import com.tma.sparking.services.http.Channel;

/**
 * Created by pkimhuy on 5/19/2017.
 */

public class ParkingField {
    private long mId;
    private int mNumber;
    private String mName;
    private Long mLastEntryId;
    private int mTotalSlot;
    private int mEmptySlot;
    private double mLatitude;
    private double mLongitude;
    private long mChannelId;
    private String mChannelName;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) {
        mNumber = number;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
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

    public Long getLastEntryId() {
        return mLastEntryId;
    }

    public void setLastEntryId(Long lastEntryId) {
        mLastEntryId = lastEntryId;
    }

    public int getTotalSlot() {
        return mTotalSlot;
    }

    public void setTotalSlot(int totalSlot) {
        mTotalSlot = totalSlot;
    }

    public int getEmptySlot() {
        return mEmptySlot;
    }

    public void setEmptySlot(int emptySlot) {
        mEmptySlot = emptySlot;
    }

    public long getChannelId() {
        return mChannelId;
    }

    public void setChannelId(long channelId) {
        mChannelId = channelId;
    }

    public String getChannelName() {
        return mChannelName;
    }

    public void setChannelName(String channelName) {
        mChannelName = channelName;
    }
}
