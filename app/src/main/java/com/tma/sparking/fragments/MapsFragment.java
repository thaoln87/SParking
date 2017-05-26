package com.tma.sparking.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tma.sparking.R;
import com.tma.sparking.fragments.utils.MapFactory;
import com.tma.sparking.models.ParkingField;
import com.tma.sparking.permissions.LocationInformationRequest;
import com.tma.sparking.permissions.LocationRequestListener;
import com.tma.sparking.services.syncdata.SyncDataManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/*
    Fragment loads a Google map that displays current location and all parking locations as markers
 */
public class MapsFragment extends SupportMapFragment
        implements OnMapReadyCallback,
        LocationRequestListener,
        GoogleMap.OnMarkerClickListener,
        Observer{

    GoogleMap mGoogleMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    private static final int MAP_ZOOM_LEVEL =  16;

    private MapFactory mMapFactory;
    private LocationInformationRequest locationInformationRequest;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mMapFactory = new MapFactory();
        locationInformationRequest = new LocationInformationRequest(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {

        if (mGoogleMap == null) {
            getMapAsync(this);
        }
    }
    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        locationInformationRequest.stopLocationService();
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap=googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().isZoomControlsEnabled();
        googleMap.setOnMarkerClickListener(this);
        //Initialize Google Play Services
        if (locationInformationRequest.isMyLocationSupported()) {
            mGoogleMap.setMyLocationEnabled(true);
        }

        // Add observer
        SyncDataManager syncDataManager = new SyncDataManager(getContext());
        syncDataManager.addObserver(this);
        syncDataManager.notifyDataAvailable(true);
        syncDataManager.startPollingService();
    }

    @Override
    public void onLocationChanged(Location location)
    {
        if (location != null && (mLastLocation == null ||
                !(mLastLocation.getLongitude() == location.getLongitude()
                        && mLastLocation.getLatitude() == location.getLatitude()))) {
            mLastLocation = location;
            if (mCurrLocationMarker != null) {
                mCurrLocationMarker.remove();
            }

            //Place current location marker
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mCurrLocationMarker = addMarker(latLng, "Current location");

            //move map camera
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, MAP_ZOOM_LEVEL));
        }
    }

    /**
     * If location permission request is granted, enable current location,
     * otherwise show "permission denied" toast
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (locationInformationRequest.checkLocationPermissionGranted(requestCode, permissions, grantResults)) {
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    /**
     * Show parking detail when user clicks on the parking marker
     * @param marker
     * @return true if success, false if ParkingField stored in marker is null
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        ParkingField parkingField = (ParkingField)marker.getTag();
        if (parkingField != null) {
            Bundle args = new Bundle();
            args.putParcelable("parkingField", parkingField);
            if (mLastLocation != null) {
                args.putDouble("location.latitude", mLastLocation.getLatitude());
                args.putDouble("location.longitude", mLastLocation.getLongitude());
            } else {
                args.putDouble("location.latitude", 0);
                args.putDouble("location.longitude", 0);
            }
            ParkingDetails parkingDetails = new ParkingDetails();
            parkingDetails.setArguments(args);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, parkingDetails).addToBackStack(null).commit();
            return true;
        }
        return false;
    }

    /**
     * Update map when data updated by SyncDataManager
     * @param observable
     * @param arg
     */
    @Override
    public void update(Observable observable, Object arg) {
        SyncDataManager syncDataManager = (SyncDataManager)observable;
        List<ParkingField> parkingFields = syncDataManager.getParkingFieldList();
        if (parkingFields.size() > 0) {
            removeAllMarker();
        }

        // TODO: for testing
        int i = 0; double temp = 0.002;
        for (ParkingField parkingField : parkingFields) {
            parkingField.setLatitude(parkingField.getLatitude() + i * temp);
            parkingField.setLongitude(parkingField.getLongitude() + i * temp);
            addParkingMarker(parkingField);
            i++;
        }
    }

    private void removeAllMarker(){
        mGoogleMap.clear();
    }

    private Marker addMarker(LatLng location, String title){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.title(title);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        return mGoogleMap.addMarker(markerOptions);
    }

    private Marker addParkingMarker(ParkingField parkingField) {
        Marker marker = mGoogleMap.addMarker(mMapFactory.createParkingFieldMakerOptions(parkingField, getContext()));
        marker.setTag(parkingField);
        return marker;
    }
}