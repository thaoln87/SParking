package com.tma.sparking.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pkimhuy on 5/19/2017.
 */

public class ParkingField implements Parcelable {
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
    private String mName;
    private int mTotalSlot;
    private int mEmptySlot;
    private double mLatitude;
    private double mLongitude;

    public ParkingField() {

    }

    protected ParkingField(Parcel in) {
        mName = in.readString();
        mTotalSlot = in.readInt();
        mEmptySlot = in.readInt();
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeInt(mTotalSlot);
        dest.writeInt(mEmptySlot);
        dest.writeDouble(mLatitude);
        dest.writeDouble(mLongitude);
    }
}
