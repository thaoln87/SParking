package com.tma.sparking.services;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.drive.events.ChangeListener;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tma.sparking.http.Channel;
import com.tma.sparking.http.ChannelRequest;
import com.tma.sparking.http.Feed;
import com.tma.sparking.http.Parking;
import com.tma.sparking.models.ParkingField;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pkimhuy on 5/19/2017.
 */

public class ParkingFieldService {
    private static final String BASE_URL = "http://api.thingspeak.com/";

    private OnFetchResult mOnFetchResult;

    public void findOne(long channelId, final int fieldId) throws Exception {
        Gson gson = createGson(fieldId);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ChannelRequest channelRequest = retrofit.create(ChannelRequest.class);
        Call<Parking> channelCall = channelRequest.getParkingChannel(channelId, fieldId);
        channelCall.enqueue(new Callback<Parking>() {
            @Override
            public void onResponse(Call<Parking> call, Response<Parking> response) {
                if (response.isSuccessful()) {
                    Parking parking = response.body();
                    ParkingField parkingField = new ParkingField();

                    int id = -1;
                    String name = null;
                    int emptySlot = 0;
                    int totalSlot = 0;
                    String fieldStatus = parking.getFeeds().get(0).getFieldStatus();
                    if (fieldStatus != null) {
                        int status = Integer.parseInt(fieldStatus);
                        for (int i = 0; i < 16; i++) {
                            if (((status & (1 << i)) >> i) == 0) {
                                emptySlot++;
                            }
                        }
                        id = fieldId;
                        name = "field" + id;
                        totalSlot = 16;
                    }
                    parkingField.setId(id);
                    parkingField.setName(name);
                    parkingField.setTotalSlot(totalSlot);
                    parkingField.setEmptySlot(emptySlot);
                    parkingField.setLastEntryId(parking.getChannel().getLastEntryId());
                    parkingField.setChannel(parking.getChannel());

                    mOnFetchResult.onSuccess(parkingField);
                } else {
                    mOnFetchResult.onSuccess(null);
                }
            }

            @Override
            public void onFailure(Call<Parking> call, Throwable t) {
                mOnFetchResult.onFailure(t);
            }
        });
    }

    public void addOnFetchResultListener(OnFetchResult onFetchResult) {
        mOnFetchResult = onFetchResult;
    }

    public interface OnFetchResult {
        void onSuccess(ParkingField parkingField);
        void onFailure(Throwable t);
    }

    private Gson createGson(final int fieldId) {
        GsonBuilder gsonBuilder = new GsonBuilder();

        FieldNamingStrategy fieldNamingStrategy = new FieldNamingStrategy() {
            @Override
            public String translateName(Field f) {
                return "field" + fieldId;
            }
        };
        gsonBuilder.setFieldNamingStrategy(fieldNamingStrategy);

        JsonDeserializer<Channel> deserializer = new JsonDeserializer<Channel>() {
            @Override
            public Channel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                Channel channel = new Channel();

                JsonObject channelJsonObj = json.getAsJsonObject();

                long id = channelJsonObj.get("id").getAsLong();
                channel.setId(id);

                String name = channelJsonObj.get("name").getAsString();
                channel.setName(name);

                String description = channelJsonObj.get("description").getAsString();
                channel.setDescription(description);

                double latitude = channelJsonObj.get("latitude").getAsDouble();
                channel.setLatitude(latitude);

                double longitude = channelJsonObj.get("longitude").getAsDouble();
                channel.setLongitude(longitude);

                long lastEntryId = channelJsonObj.get("last_entry_id").getAsLong();
                channel.setLastEntryId(lastEntryId);

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US);
                Date updatedAt = null;
                try {
                    updatedAt = dateFormat.parse(channelJsonObj.get("updated_at").getAsString());
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                channel.setUpdatedAt(updatedAt);

                Date createdAt = null;
                try {
                    createdAt = dateFormat.parse(channelJsonObj.get("created_at").getAsString());
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                channel.setCreatedAt(createdAt);

                List<com.tma.sparking.http.Field> fields = new ArrayList<>();
                String fieldName = "field1";
                int i = 1;
                while (channelJsonObj.get(fieldName) != null) {
                    com.tma.sparking.http.Field field = new com.tma.sparking.http.Field();
                    field.setFieldId(i);
                    field.setFieldName(fieldName);
                    String fieldDescription = channelJsonObj.get(fieldName).getAsString();
                    field.setFieldDescription(fieldDescription);

                    fields.add(field);

                    i++;
                    fieldName = "field" + i;
                }
                channel.setFields(fields);

                return channel;
            }
        };

        gsonBuilder.registerTypeHierarchyAdapter(Channel.class, deserializer);

        return gsonBuilder.create();
    }
}
