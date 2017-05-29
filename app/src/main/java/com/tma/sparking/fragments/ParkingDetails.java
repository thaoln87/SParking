package com.tma.sparking.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.tma.sparking.AdapterRecyclerView;
import com.tma.sparking.R;
import com.tma.sparking.fragments.utils.MapFactory;
import com.tma.sparking.interfaces.RecyclerOnItemClickListener;
import com.tma.sparking.models.ParkingField;
import com.tma.sparking.models.TimeParking;
import com.tma.sparking.utils.GoogleMapUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ntmhanh on 5/23/2017.
 */

public class ParkingDetails extends Fragment implements com.tma.sparking.utils.GoogleMapUtilsListener {
    RecyclerView recyclerView;
    AdapterRecyclerView adapter;
    private GoogleMap mGoogleMap;
    private ParkingField mParkingField;
    @BindView(R.id.mapView)  MapView mMapView;
    private MapFactory mMapFactory;
    List<TimeParking> timeParkingList;
    @BindView(R.id.parking_name)  TextView tvParkingName;
    @BindView(R.id.parking_address)  TextView tvParkingAddress;
    @BindView(R.id.distance_from_current_location)  TextView tvDistance;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.parking_details, container, false);

        ButterKnife.bind(this, view);

        mMapFactory = new MapFactory();

        //for crate home button
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_36dp);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewList);
        adapter = new AdapterRecyclerView(createTimeListObject());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        mMapView.onCreate(savedInstanceState);

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
                mGoogleMap.getUiSettings().setAllGesturesEnabled(false);
                setUpMap();
            }
        });

        mMapView.setClickable(false);

        adapter.SetOnItemClickListener(new RecyclerOnItemClickListener() {
            @Override
            public void onItemClick(View childView, int position) {
                TimeParking timeParking = timeParkingList.get(position - 1);
                String price = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"))
                        .format(timeParking.valueOfTime > 0 ? timeParking.valueOfTime * 15000 : 15000);
                TextView tvPrice = ButterKnife.findById(getView(), R.id.tvPrice);
                tvPrice.setText(price);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Load parking field details
        loadParkingFieldDetails(view);
    }

    private void loadParkingFieldDetails(View view){
        Bundle bundle = getArguments();
        mParkingField= bundle.getParcelable("parkingField");
        if (mParkingField != null) {
            LatLng parkingLocation = new LatLng(mParkingField.getLatitude(), mParkingField.getLongitude());
            GoogleMapUtils googleMapUtils = new GoogleMapUtils(getContext(), this);
            String parkingAddress = googleMapUtils.getCompleteAddress(parkingLocation.latitude, parkingLocation.longitude);
            double locationLat = bundle.getDouble("location.latitude");
            double locationLng = bundle.getDouble("location.longitude");
            LatLng currentLocation = new LatLng(locationLat, locationLng);
            googleMapUtils.drivingDistanceBetweenTwoLocation(currentLocation, parkingLocation);
            tvParkingName.setSelected(true);
            tvParkingName.setText(mParkingField.getName());

            tvParkingAddress.setSelected(true);
            tvParkingAddress.setText(parkingAddress);

            tvDistance.setText(getString(R.string.calculating));
            ((TextView)  ButterKnife.findById(view, R.id.total_slots)).setText(String.valueOf(mParkingField.getTotalSlot()));
            ((TextView)  ButterKnife.findById(view, R.id.empty_slots)).setText(String.valueOf(mParkingField.getEmptySlot()));
            setUpMap();

            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mParkingField.getName());
        }
    }

    /**
     * Set up the map
     */
    private void setUpMap() {
        if (mGoogleMap != null && mParkingField != null) {
            LatLng latLng = new LatLng(mParkingField.getLatitude(), mParkingField.getLongitude());
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
            mGoogleMap.addMarker(mMapFactory.createParkingFieldMakerOptions(mParkingField, getContext()));
        }
    }

    @Override
    public void displayDistance(String distance) {
        if (tvDistance != null) {
            tvDistance.setText(distance);
        }
    }

    public List<TimeParking> createTimeListObject(){
        TimeParking timeParking1 = new TimeParking("1 Giờ", 1);
        TimeParking timeParking2 = new TimeParking("2 Giờ", 2);
        TimeParking timeParking3 = new TimeParking("3 Giờ", 3);
        TimeParking timeParking4 = new TimeParking("4 Giờ", 4);
        TimeParking timeParking5 = new TimeParking("5 Giờ", 5);
        TimeParking timeParking6 = new TimeParking("6 Giờ", 6);
        TimeParking timeParking7 = new TimeParking("7 Giờ", 7);
        TimeParking timeParking8 = new TimeParking("8 Giờ", 8);
        timeParkingList = new ArrayList<>();
        timeParkingList.add(timeParking1);
        timeParkingList.add(timeParking2);
        timeParkingList.add(timeParking3);
        timeParkingList.add(timeParking4);
        timeParkingList.add(timeParking5);
        timeParkingList.add(timeParking6);
        timeParkingList.add(timeParking7);
        timeParkingList.add(timeParking8);
        return timeParkingList;
    }
}
