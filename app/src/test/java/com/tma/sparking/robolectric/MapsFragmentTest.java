package com.tma.sparking.robolectric;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.tma.sparking.BuildConfig;
import com.tma.sparking.MainActivity;
import com.tma.sparking.R;
import com.tma.sparking.fragments.ParkingDetails;
import com.tma.sparking.models.ParkingField;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by ntmhanh on 6/6/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MapsFragmentTest {
    private MainActivity mActivity;

    @Before
    public void init(){
        mActivity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void makerOnClick_ClickOnAParkingMarker_goToParkingDetails(){
        // Set up Bundle
        ParkingField parkingField = new ParkingField();
        Bundle args = new Bundle();
        args.putParcelable("parkingField", parkingField);
        ParkingDetails parkingDetails = new ParkingDetails();
        parkingDetails.setArguments(args);
        //Action
        mActivity.onMarkerClick(args);
        Fragment fragment = mActivity.getSupportFragmentManager().findFragmentById(R.id.content_main);
        //Assert
        Assert.assertEquals("ParkingDetails", fragment.getTag());
    }
    @Test
    public void makerOnClick_ClickOnLocationMarker_doNothing(){
        // Set up Bundle
        Bundle args = new Bundle();
        //Action
        mActivity.onMarkerClick(args);
        Fragment fragment = mActivity.getSupportFragmentManager().findFragmentById(R.id.content_main);
        //Assert
        Assert.assertNotEquals("ParkingDetails", fragment.getTag());
    }

}
