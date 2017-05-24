package com.tma.sparking.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.util.List;
import java.util.Locale;

/**
 * Created by lnthao on 5/24/2017.
 */

public class GoogleMapUtils {
    private Context mContext;

    public GoogleMapUtils(Context context) {
        mContext = context;
    }

    public String getCompleteAddress(double latitude, double longitude) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }


    /**
     * Distance between two location
     * @param firstCoordinate
     * @param secondCoordinate
     * @return
     */
    public double distanceBetweenTwoLocation(LatLng firstCoordinate, LatLng secondCoordinate) {
        double distance = SphericalUtil.computeDistanceBetween(firstCoordinate, secondCoordinate);
        return distance;
    }
}
