package com.tma.sparking.services.http;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Retrofit interface for requesting channel from server
 */
interface ChannelRequest {
    @GET("channels/{channel_id}/fields/{field_id}.json?api_key=TI6JXFG7XTXLQ249&results=1")
    Call<Parking> getParkingChannel(@Path("channel_id") long channelId, @Path("field_id") long fieldId);
}
