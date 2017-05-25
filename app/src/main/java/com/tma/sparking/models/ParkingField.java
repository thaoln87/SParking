package com.tma.sparking.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.tma.sparking.services.http.Channel;

/**
 * Created by pkimhuy on 5/19/2017.
 */

public class ParkingField implements Parcelable {
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

    public ParkingField(){

    }

    protected ParkingField(Parcel in) {
        mId = in.readLong();
        mNumber = in.readInt();
        mName = in.readString();
        mTotalSlot = in.readInt();
        mEmptySlot = in.readInt();
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
        mChannelId = in.readLong();
        mChannelName = in.readString();
    }

    public static final Creator<ParkingField> CREATOR = new Creator<ParkingField>() {
        @Override
        public ParkingField createFromParcel(Parcel in) {
            return new ParkingField(in);
        }

        @Override
        public ParkingField[] newArray(int size) {
            return new ParkingField[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeInt(mNumber);
        dest.writeString(mName);
        dest.writeLong(mLastEntryId);
        dest.writeInt(mTotalSlot);
        dest.writeInt(mEmptySlot);
        dest.writeDouble(mLatitude);
        dest.writeDouble(mLongitude);
        dest.writeLong(mChannelId);
        dest.writeString(mChannelName);
    }
}
