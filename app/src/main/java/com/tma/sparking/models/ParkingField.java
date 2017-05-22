package com.tma.sparking.models;

import com.tma.sparking.services.http.Channel;

/**
 * Created by pkimhuy on 5/19/2017.
 */

public class ParkingField {
    private int mId;
    private String mName;
    private Channel mChannel;
    private Long mLastEntryId;
    private int mTotalSlot;
    private int mEmptySlot;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Channel getChannel() {
        return mChannel;
    }

    public void setChannel(Channel channel) {
        mChannel = channel;
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
}
