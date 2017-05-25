package com.tma.sparking;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.gms.maps.model.LatLng;
import com.tma.sparking.utils.GoogleMapUtils;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by lnthao on 5/24/2017.
 */

@RunWith(AndroidJUnit4.class)
public class GoogleMapUtilsIntrumentedTest {
    private Context mContext;
    private GoogleMapUtils mGoogleMapUtils;

    @Before
    public void setUp(){
        mContext = InstrumentationRegistry.getTargetContext();
        mGoogleMapUtils = new GoogleMapUtils(mContext, null);
    }

    @Test
    public void getCompleteAddress_validLocation_returnFullAddress(){
        // Arrange
        String expectedAddress = "104/9A Tổ 21 Kp2, " + "Đông Hưng Thuận, " + "Quận 12, " + "Hồ Chí Minh";
        LatLng location = new LatLng(10.8526792, 106.6350251);
        // Act
        String actualAddress = mGoogleMapUtils.getCompleteAddress(location.latitude, location.longitude);
        // Assert
        Assert.assertEquals(expectedAddress, actualAddress);
    }

    @Test
    public void distanceBetweenTwoLocation_fromCVPMQTToChoCau_returnAbout700meters(){
        // Arrange
        LatLng location1 = new LatLng(10.8526792, 106.6350251);
        LatLng location2 = new LatLng(10.846462, 106.636527);
        double expectedDistance = 710.512724995018; // unit is meter
        // Act
        double distance = mGoogleMapUtils.distanceBetweenTwoLocation(location1, location2);
        // Assert
        Assert.assertEquals(expectedDistance, distance);

    }
}
