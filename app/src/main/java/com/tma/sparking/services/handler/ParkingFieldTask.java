package com.tma.sparking.services.handler;

import com.tma.sparking.models.ParkingField;

import java.util.List;

class ParkingFieldTask {
    private List<ParkingField> mParkingFields;
    private String mErrorMessage;
    private boolean mHasError;

    public ParkingFieldTask() {
        mHasError = false;
    }

    public List<ParkingField> getParkingFields() {
        return mParkingFields;
    }

    public void setParkingFields(List<ParkingField> parkingFields) {
        mParkingFields = parkingFields;
    }

    public void setError(String errorMessage) {
        mErrorMessage = errorMessage;
        mHasError = true;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public boolean hasError() {
        return mHasError;
    }
}
