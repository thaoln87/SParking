package com.tma.sparking.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;
import com.tma.sparking.R;
import com.tma.sparking.fragments.utils.MapFactory;
import com.tma.sparking.models.ParkingField;
import com.tma.sparking.services.parkinghandler.ParkingDataCallback;
import com.tma.sparking.services.parkinghandler.ParkingManager;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends SupportMapFragment
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener {

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    private List<Marker> markers = new ArrayList<>();
    private List<LatLng> carCoordinates = new ArrayList<>();
    private static final int MAP_ZOOM_LEVEL =  16;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private MapFactory mMapFactory;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mMapFactory = new MapFactory();
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
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap=googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().isZoomControlsEnabled();
        googleMap.setOnMarkerClickListener(this);
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }

//        // Add observer
//        SyncDataManager syncDataManager = new SyncDataManager(getContext());
//        syncDataManager.addObserver(this);
//        syncDataManager.notifyDataAvailable(true);
//        syncDataManager.startPollingService();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    @Override
    public void onLocationChanged(Location location)
    {
//        if (mLastLocation == null ||
//                !(mLastLocation.getLongitude() == location.getLongitude()
//                        && mLastLocation.getLatitude() == location.getLatitude())) {
        if (mLastLocation == null) {
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

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
    /**
     * Drawing your circle and display car parks around your site
     * @param latLng
     * @param positions
     */
    private void displayCarParksAroundYourSite(LatLng latLng, List<LatLng> positions) {
        for (LatLng position : positions) {
            Marker marker = mGoogleMap.addMarker(
                    new MarkerOptions()
                            .position(position)
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                            .visible(false)); // Invisible for now
            markers.add(marker);
        }

        //Draw your circle
        mGoogleMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(3000)
                .strokeColor(Color.rgb(0, 136, 255))
                .fillColor(Color.argb(20, 0, 136, 255)));

        for (Marker marker : markers) {
            if (SphericalUtil.computeDistanceBetween(latLng, marker.getPosition()) < 3000) {
                marker.setVisible(true);
            }
        }
    }

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

//    @Override
//    public void update(Observable observable, Object arg) {
//        SyncDataManager syncDataManager = (SyncDataManager)observable;
//        List<ParkingField> parkingFields = syncDataManager.getParkingFieldList();
//        if (parkingFields.size() > 0) {
//            removeAllMarker();
//        }
//
//        // TODO: for testing
//        int i = 0; double temp = 0.002;
//        for (ParkingField parkingField : parkingFields) {
//            parkingField.setLatitude(parkingField.getLatitude() + i * temp);
//            parkingField.setLongitude(parkingField.getLongitude() + i * temp);
//            addParkingMarker(parkingField);
//            i++;
//        }
//    }

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