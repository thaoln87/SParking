package com.tma.sparking.services.handler;

import com.tma.sparking.models.ParkingField;

import java.util.List;

class ParkingFieldTask {
    private List<ParkingField> mParkingFields;
    private String mErrorMessage;
    private boolean mHasError;

    ParkingFieldTask() {
        mHasError = false;
    }

    List<ParkingField> getParkingFields() {
        return mParkingFields;
    }

    void setParkingFields(List<ParkingField> parkingFields) {
        mParkingFields = parkingFields;
    }

    void setError(String errorMessage) {
        mErrorMessage = errorMessage;
        mHasError = true;
    }

    String getErrorMessage() {
        return mErrorMessage;
    }

    boolean hasError() {
        return mHasError;
    }
}
