package com.tma.sparking.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tma.sparking.R;


import static android.content.Context.LOCATION_SERVICE;


public class MapsFragment extends Fragment {
    MapView mMapView;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                getYourLocation();
            }
        });

        return rootView;
    }

    private void getYourLocation() {

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location lastLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (lastLocation != null)
                {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), 13));

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()))      // Sets the center of the map to location user
                            .zoom(15)                   // Sets the zoom
                            .bearing(90)                // Sets the orientation of the camera to east
                            .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                            .build();                   // Creates a CameraPosition from the builder
                    googleMap.addMarker(new MarkerOptions().position(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()))
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                            .title("Marker Title").snippet("Marker Description"));
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}