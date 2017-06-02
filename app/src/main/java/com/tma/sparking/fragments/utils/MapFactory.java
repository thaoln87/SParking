package com.tma.sparking.fragments.utils;

import android.content.Context;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tma.sparking.R;
import com.tma.sparking.models.ParkingField;
import com.tma.sparking.utils.CharacterIconResource;

/**
 * Created by QuanLe on 5/26/2017.
 */

public class MapFactory {

    /**
     * Create a MarkerOptions for a ParkingField
     *
     * @param parkingField
     * @return
     */
    public MarkerOptions createParkingFieldMakerOptions(ParkingField parkingField, Context context) {
        LatLng location = new LatLng(parkingField.getLatitude(), parkingField.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.title(parkingField.getName());
        if (context != null) {
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(new CharacterIconResource(context,
                    String.valueOf(parkingField.getEmptySlot()), R.drawable.ic_location_filter_green).getBitmap()));
        } else {
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        }
        return markerOptions;
    }
}
