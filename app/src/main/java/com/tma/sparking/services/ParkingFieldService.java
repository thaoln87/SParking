package com.tma.sparking.services;

import com.google.gson.Gson;
import com.tma.sparking.services.http.ChannelRequest;
import com.tma.sparking.services.http.GsonParser;
import com.tma.sparking.services.http.Parking;
import com.tma.sparking.models.ParkingField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Service for reading data from server
 */
public class ParkingFieldService {
    private static final String FIELD_PREFIX = "field";
    private static final String BASE_URL = "http://api.thingspeak.com/";
    private static final long CHANNEL_ID = 270768;

    /**
     * Get all parking fields from web service
     * @return a list of parking fields
     */
    public List<ParkingField> findAll() {
        List<ParkingField> parkingFields = new ArrayList<>();

        for (int i = 1; i <= 8; i++) {
            ParkingField parkingField = findOne(CHANNEL_ID, i);

            if (parkingField != null) {
                parkingFields.add(parkingField);
            }
        }

        return parkingFields;
    }

    /**
     * Find parking field by channel id and field id
     * return null if channel or field does not exist
     * @param channelId id of parking channel
     * @param fieldId id of parking field
     */
    private ParkingField findOne(long channelId, final int fieldId) {
        Gson gson = GsonParser.createGsonParser(fieldId);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ChannelRequest channelRequest = retrofit.create(ChannelRequest.class);

        ParkingField parkingField = null;
        Call<Parking> channelCall = channelRequest.getParkingChannel(channelId, fieldId);

        try {
            Response<Parking> response = channelCall.execute();
            if (response.isSuccessful()) {
                Parking parking = response.body();

                String fieldStatus = parking.getFeeds().get(0).getFieldStatus();
                if (fieldStatus != null) {
                    int emptySlot = 0;

                    int status = Integer.parseInt(fieldStatus);
                    for (int i = 0; i < 16; i++) {
                        if (((status & (1 << i)) >> i) == 0) emptySlot++;
                    }
                    parkingField = new ParkingField();
                    parkingField.setName(FIELD_PREFIX + fieldId);
                    parkingField.setTotalSlot(16);
                    parkingField.setEmptySlot(emptySlot);
                    parkingField.setLatitude(parking.getChannel().getLatitude());
                    parkingField.setLongitude(parking.getChannel().getLongitude());
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return parkingField;
    }
}
