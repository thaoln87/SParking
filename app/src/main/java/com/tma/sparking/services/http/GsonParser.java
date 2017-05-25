package com.tma.sparking.services.http;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Custom json parser for parsing an awkward json data...
 */
public class GsonParser {
    private static final String FIELD_PREFIX = "field";

    /**
     * Create a custom Gson parser
     *
     * @param fieldId dynamic field id property name to custom gson naming strategy
     * @return gson parser
     */
    public static Gson createGsonParser(final int fieldId) {
        GsonBuilder gsonBuilder = new GsonBuilder();

        FieldNamingStrategy fieldNamingStrategy = new FieldNamingStrategy() {
            @Override
            public String translateName(java.lang.reflect.Field f) {
                return FIELD_PREFIX + fieldId;
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

                Date updatedAt = getDateValue(channelJsonObj, "updated_at");
                channel.setUpdatedAt(updatedAt);

                Date createdAt = getDateValue(channelJsonObj, "created_at");
                channel.setCreatedAt(createdAt);

                List<Field> fields = new ArrayList<>();
                int i = 1;
                String fieldName = FIELD_PREFIX + i;
                while (channelJsonObj.get(fieldName) != null) {
                    com.tma.sparking.services.http.Field field = new com.tma.sparking.services.http.Field();
                    field.setFieldId(i);
                    field.setFieldName(fieldName);
                    String fieldDescription = channelJsonObj.get(fieldName).getAsString();
                    field.setFieldDescription(fieldDescription);

                    fields.add(field);

                    i++;
                    fieldName = FIELD_PREFIX + i;
                }
                channel.setFields(fields);

                return channel;
            }
        };

        gsonBuilder.registerTypeHierarchyAdapter(Channel.class, deserializer);

        return gsonBuilder.create();
    }

    /**
     * Parse date from standard ISO 8601 format
     *
     * @param jsonObject a json object contain date property
     * @param fieldName date property name of jsonObject
     * @return a date object
     */
    private static Date getDateValue(JsonObject jsonObject, String fieldName) {
        Date date = null;

        String pattern = "yyyy-MM-dd'T'HH:mm:ssZ";
        DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.US);
        try {
            date = dateFormat.parse(jsonObject.get(fieldName).getAsString());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return date;
    }
}
