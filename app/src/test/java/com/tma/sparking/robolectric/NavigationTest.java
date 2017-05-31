package com.tma.sparking.robolectric;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.tma.sparking.BuildConfig;
import com.tma.sparking.MainActivity;
import com.tma.sparking.R;
import com.tma.sparking.fragments.ParkingDetails;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by ntmhanh on 5/31/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class NavigationTest {
    private MainActivity mActivity;
    private ParkingDetails mParkingDetails;
    @Before
    public void init() {
        mActivity = Robolectric.setupActivity(MainActivity.class);
        mParkingDetails = new ParkingDetails();
    }

    @Test
    public void onBackPressed_clickOnBackButtonOnAppBarGoToMainActivity_returnTrue(){
        // Setup Bundle
        Bundle args = new Bundle();
        args.putParcelable("parkingField", null);
        mParkingDetails.setArguments(args);
        startFragment(mParkingDetails);
        // Action
        mActivity.onBackPressed();
        int count =  mActivity.getSupportFragmentManager().getBackStackEntryCount();
        // Assertions
        Assert.assertEquals(0, count);

    }
    private void startFragment(Fragment fragment) {
        mActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, fragment).commit();
    }
}
