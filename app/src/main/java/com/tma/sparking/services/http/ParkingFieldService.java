package com.tma.sparking.services.http;

import com.google.gson.Gson;
import com.tma.sparking.services.http.ChannelRequest;
import com.tma.sparking.services.http.GsonParser;
import com.tma.sparking.services.http.Parking;
import com.tma.sparking.models.ParkingField;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Service for reading data from server
 */
public interface ParkingFieldService {
    /**
     * Find parking field by channel id and field id
     * return null if channel or field does not exist
     *
     * @param channelId id of parking channel
     * @param fieldId id of parking field
     */
    ParkingField findOne(long channelId, final int fieldId);
}
