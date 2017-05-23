package com.tma.sparking.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;
import com.tma.sparking.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;


public class MapsFragment extends Fragment implements GoogleMap.OnMarkerClickListener {
    MapView mMapView;
    private GoogleMap googleMap;
    private List<Marker> markers = new ArrayList<>();
    private List<LatLng> carCoordinates = new ArrayList<>();

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
        //Location lastLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));

        //if (lastLocation != null)
                {
                    //LatLng yourCoordinate = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                    // TODO: get location instead of hardcode
                    LatLng yourCoordinate = new LatLng(10.536, 12.458646);

                    //Log.d("MyLatLng", lastLocation.getLatitude() + ", " +  lastLocation.getLongitude());
                    LatLng carCoordinate = new LatLng(yourCoordinate.latitude - 0.00008746588293, yourCoordinate.longitude - 0.0000085939481);
                    carCoordinates.add(carCoordinate);
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            yourCoordinate, 13));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(yourCoordinate.latitude, yourCoordinate.longitude))      // Sets the center of the map to location user
                            .zoom(15)                   // Sets the zoom
                            .bearing(90)                // Sets the orientation of the camera to east
                            .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                            .build();                   // Creates a CameraPosition from the builder
                    googleMap.addMarker(new MarkerOptions().position(yourCoordinate)
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                            .title("Marker Title").snippet("Marker Description"));
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    googleMap.getUiSettings().isZoomControlsEnabled();
                    displayCarParksAroundYourSite(yourCoordinate, carCoordinates);
                    googleMap.setOnMarkerClickListener(this);
                }
    }

    /**
     * Drawing your circle and display car parks around your site
     * @param latLng
     * @param positions
     */
    private void displayCarParksAroundYourSite(LatLng latLng, List<LatLng> positions) {
        for (LatLng position : positions) {
            Marker marker = googleMap.addMarker(
                    new MarkerOptions()
                            .position(position)
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                            .visible(false)); // Invisible for now
            markers.add(marker);
        }

        //Draw your circle
         googleMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(300)
                .strokeColor(Color.argb(20, 0, 136, 255))
                .fillColor(Color.argb(20, 0, 136, 255)));

        for (Marker marker : markers) {
            if (SphericalUtil.computeDistanceBetween(latLng, marker.getPosition()) < 300) {
                marker.setVisible(true);
            }
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

    @Override
    public boolean onMarkerClick(Marker marker) {
        Bundle args = new Bundle();
        args.putString("markerTitle",marker.getTitle());
        args.putString("makerDescriptions", marker.getSnippet());
        ParkingDetails parkingDetails = new ParkingDetails();
        parkingDetails.setArguments(args);
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, parkingDetails);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        return true;
    }
}