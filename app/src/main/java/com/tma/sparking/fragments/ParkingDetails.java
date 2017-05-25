package com.tma.sparking.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.tma.sparking.AdapterRecyclerView;
import com.tma.sparking.R;
import com.tma.sparking.models.ParkingField;
import com.tma.sparking.utils.GoogleMapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ntmhanh on 5/23/2017.
 */

public class ParkingDetails extends Fragment {
    RecyclerView recyclerView;
    AdapterRecyclerView adapter;
    List<String> data;
    private GoogleMap mGoogleMap;
    private ParkingField mParkingField;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        MapsInitializer.initialize(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.parking_details, container, false);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        //set toolbar appearance
        //toolbar.setBackground(R.color.material_blue_grey_800);

        //for crate home button
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setHomeButtonEnabled(true);
        activity.getSupportActionBar().setTitle("Điểm đỗ 103");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewList);
        adapter = new AdapterRecyclerView(createData());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        MapView mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        Log.d("test", "map created");
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.d("test", "map ready");
                mGoogleMap = googleMap;
                setUpMap();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        final ParkingField parkingField = bundle.getParcelable("parkingField");

        if (parkingField != null) {
            GoogleMapUtils googleMapUtils = new GoogleMapUtils(getContext());
            String parkingAddress = googleMapUtils.getCompleteAddress(parkingField.getLatitude(), parkingField.getLongitude());
            ((TextView) getActivity().findViewById(R.id.parking_name)).setText(parkingField.getName());
            ((TextView) getActivity().findViewById(R.id.parking_address)).setText(parkingAddress);
            mParkingField = parkingField;
            setUpMap();
        }


    }

    public void setUpMap() {
        Log.d("test", "Hello setUp Map");
        if (mGoogleMap != null && mParkingField != null) {
            Log.d("test", "setUpMap");
            LatLng latLng = new LatLng(mParkingField.getLatitude(), mParkingField.getLongitude());
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        }
    }

    public List<String> createData() {
        data = new ArrayList<>();
        data.add("1H");
        data.add("2H");
        data.add("3H");
        data.add("4H");
        data.add("5H");
        data.add("6H");
        data.add("7H");
        data.add("8H");
        data.add("9H");
        return data;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }
}
