package com.tma.sparking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.tma.sparking.fragments.MapsFragment;
import com.tma.sparking.fragments.ParkingDetails;
import com.tma.sparking.models.ParkingField;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by lnthao on 5/30/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ParkingDetailsTest {
    private ParkingDetails mParkingDetails;
    private MainActivity mActivity;
    private static String PARKING_NAME = "Parking1";
    private static int EMPTY_SLOTS = 12;
    private static int TOTAL_SLOTS = 15;
    private static double LAT = 10.8526792;
    private static double LNG = 106.6350251;


    @Before
    public void setUp() {
        mActivity = Robolectric.setupActivity(MainActivity.class);
        mParkingDetails = new ParkingDetails();
    }

    @After
    public void cleanUp() {
        removeFragment(mParkingDetails);
    }

    @Test
    public void onViewCreated_nullParkingDetailsObject_loadDefaultValues() throws Exception {
        Bundle args = new Bundle();
        args.putParcelable("parkingField", null);
        mParkingDetails.setArguments(args);
        startFragment(mParkingDetails);
        // Get views
        View parkingDetailsView = mParkingDetails.getView();
        TextView parkingName = (TextView)parkingDetailsView.findViewById(R.id.parking_name);
        TextView totalSlots = (TextView)parkingDetailsView.findViewById(R.id.total_slots);
        TextView emptySlots = (TextView)parkingDetailsView.findViewById(R.id.empty_slots);
        // Assertions
        Assert.assertEquals(mActivity.getResources().getString(R.string.default_parking_name), parkingName.getText());
        Assert.assertEquals(mActivity.getResources().getString(R.string.default_total_slots), totalSlots.getText());
        Assert.assertEquals(mActivity.getResources().getString(R.string.default_empty_slots), emptySlots.getText());
    }

    @Test
    public void onViewCreated_notNullParkingDetailsObject_loadParking() throws Exception {
        // Create parkingField
        ParkingField parkingField = new ParkingField();
        parkingField.setName(PARKING_NAME);
        parkingField.setEmptySlot(EMPTY_SLOTS);
        parkingField.setTotalSlot(TOTAL_SLOTS);
        parkingField.setLongitude(LNG);
        parkingField.setLatitude(LAT);
        // Set parkingField to bundle
        Bundle args = new Bundle();
        args.putDouble("location.latitude", LAT - 0.002);
        args.putDouble("location.longitude", LNG - 0.002);
        args.putParcelable("parkingField", parkingField);
        mParkingDetails.setArguments(args);
        startFragment(mParkingDetails);
        // Get views
        View parkingDetailsView = mParkingDetails.getView();
        TextView parkingName = (TextView)parkingDetailsView.findViewById(R.id.parking_name);
        TextView totalSlots = (TextView)parkingDetailsView.findViewById(R.id.total_slots);
        TextView emptySlots = (TextView)parkingDetailsView.findViewById(R.id.empty_slots);
        // Assertions
        Assert.assertEquals(parkingField.getName(), parkingName.getText());
        Assert.assertEquals(String.valueOf(parkingField.getTotalSlot()), totalSlots.getText());
        Assert.assertEquals(String.valueOf(parkingField.getEmptySlot()), emptySlots.getText());
    }

    private void removeFragment(Fragment fragment) {
        mActivity.getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    private void startFragment(Fragment fragment) {
        mActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, fragment).commit();
    }
}
