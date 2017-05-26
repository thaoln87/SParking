package com.tma.sparking.services.http;

import com.tma.sparking.models.ParkingField;

/**
 * Created by pkimhuy on 5/26/2017.
 */

public class FakeParkingFieldService implements ParkingFieldService {
    @Override
    public ParkingField findOne(long channelId, int fieldId) {
        ParkingField parkingField = new ParkingField();

        parkingField.setLatitude(10.2);
        parkingField.setLongitude(22.2);
        parkingField.setNumber(2);
        parkingField.setTotalSlot(32);
        parkingField.setName("hehe");
        parkingField.setChannelId(12345);
        parkingField.setChannelName("huhu");
        parkingField.setEmptySlot(3);
        parkingField.setId(2);
        parkingField.setLastEntryId(23423L);

        return parkingField;
    }
}
