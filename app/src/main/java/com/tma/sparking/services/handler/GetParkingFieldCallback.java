package com.tma.sparking.services.handler;

import com.tma.sparking.models.ParkingField;

import java.util.List;

public interface GetParkingFieldCallback {
    void onParkingFieldsLoaded(List<ParkingField> parkingFields);
    void onDataUnavailable(String message);
}
