package com.tma.sparking.services.parkinghandler;

import com.tma.sparking.models.ParkingField;

import java.util.List;

public interface ParkingDataCallback {
    void onParkingFieldsLoaded(List<ParkingField> parkingFields);
}
