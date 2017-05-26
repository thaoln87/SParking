package com.tma.sparking.services.http;

import com.google.gson.Gson;
import com.tma.sparking.models.ParkingField;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * An implement of ParkingFieldService using Retrofit
 */
public class RetrofitParkingFieldService implements ParkingFieldService {
    private static final String FIELD_PREFIX = "field";

    public ParkingField findOne(long channelId, final int fieldId) {
        Gson gson = GsonParser.createGsonParser(fieldId);

        ChannelRequest channelRequest = RetrofitRequestBuilder.createRequest(ChannelRequest.class, gson);

        ParkingField parkingField = null;
        Call<Parking> channelCall = channelRequest.getParkingChannel(channelId, fieldId);

        try {
            Response<Parking> response = channelCall.execute();
            if (response.isSuccessful()) {
                Parking parking = response.body();

                String fieldStatus = parking.getFeeds().get(0).getFieldStatus();
                if (fieldStatus != null) {
                    int emptySlot = 0;
                    int totalSlot = 16;
                    int status = Integer.parseInt(fieldStatus);
                    for (int i = 0; i < totalSlot; i++) {
                        if (getBitAtPosition(status, i) == 0) emptySlot++;
                    }
                    parkingField = new ParkingField();
                    parkingField.setNumber(fieldId);
                    parkingField.setName(FIELD_PREFIX + fieldId);
                    parkingField.setTotalSlot(totalSlot);
                    parkingField.setEmptySlot(emptySlot);
                    parkingField.setLastEntryId(parking.getChannel().getLastEntryId());
                    parkingField.setLatitude(parking.getChannel().getLatitude());
                    parkingField.setLongitude(parking.getChannel().getLongitude());
                    parkingField.setChannelId(parking.getChannel().getId());
                    parkingField.setChannelName(parking.getChannel().getName());
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return parkingField;
    }

    private int getBitAtPosition(int number, int position) {
        return (number & (1 << position)) >> position;
    }
}
