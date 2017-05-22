package com.tma.sparking;

import com.google.gson.Gson;
import com.tma.sparking.services.http.Channel;
import com.tma.sparking.services.http.GsonParser;
import com.tma.sparking.services.http.Parking;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

/**
 * Created by pkimhuy on 5/22/2017.
 */

public class GsonParserTest {
    private String mJsonData;

    @Before
    public void prepareJsonData() throws Exception {
        URL url = getClass().getClassLoader().getResource("parking.json");
        InputStream is = url.openConnection().getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line = null;
        StringBuilder jsonDataBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            jsonDataBuilder.append(line);
        }

        mJsonData = jsonDataBuilder.toString();
    }

    @Test
    public void createGson_ParkingJsonData_NotNullParkingObject() throws Exception {
        Gson gson = GsonParser.createGsonParser(2);
        Parking actualParking = gson.fromJson(mJsonData, Parking.class);

        Assert.assertNotNull(actualParking);
        Assert.assertEquals(270768, actualParking.getChannel().getId(), 0L);
    }
}
