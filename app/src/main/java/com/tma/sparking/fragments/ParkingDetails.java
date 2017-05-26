package com.tma.sparking.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tma.sparking.AdapterRecyclerView;
import com.tma.sparking.R;
import com.tma.sparking.interfaces.RecyclerOnItemClickListener;
import com.tma.sparking.models.ParkingField;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ntmhanh on 5/23/2017.
 */

public class ParkingDetails extends Fragment implements com.tma.sparking.utils.GoogleMapUtilsListener {
    RecyclerView recyclerView;
    AdapterRecyclerView adapter;
    List<String> data;
    private ParkingField mParkingField;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.parking_details, container, false);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        //for crate home button
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setHomeButtonEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_backward);
        activity.getSupportActionBar().setTitle("Điểm đỗ 103");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewList);
        adapter = new AdapterRecyclerView(createData());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.SetOnItemClickListener(new RecyclerOnItemClickListener() {
            @Override
            public void onItemClick(View childView, int position) {
                String price = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"))
                        .format((position + 1) * 15000);
                TextView tvPrice = (TextView) getView().findViewById(R.id.tvPrice);
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
       /* mParkingField= bundle.getParcelable("parkingField");
        if (mParkingField != null) {
            LatLng parkingLocation = new LatLng(mParkingField.getLatitude(), mParkingField.getLongitude());
            GoogleMapUtils googleMapUtils = new GoogleMapUtils(getContext(), this);
            String parkingAddress = googleMapUtils.getCompleteAddress(parkingLocation.latitude, parkingLocation.longitude);
            double locationLat = bundle.getDouble("location.latitude");
            double locationLng = bundle.getDouble("location.longitude");
            LatLng currentLocation = new LatLng(locationLat, locationLng);
            googleMapUtils.drivingDistanceBetweenTwoLocation(currentLocation, parkingLocation);
            TextView tvParkingName = ((TextView) view.findViewById(R.id.parking_name));
            tvParkingName.setSelected(true);
            tvParkingName.setText(mParkingField.getName());
            TextView tvParkingAddress = ((TextView) view.findViewById(R.id.parking_address));
            tvParkingAddress.setSelected(true);
            tvParkingAddress.setText(parkingAddress);
            ((TextView) view.findViewById(R.id.total_slots)).setText(String.valueOf(mParkingField.getTotalSlot()));
            ((TextView) view.findViewById(R.id.empty_slots)).setText(String.valueOf(mParkingField.getEmptySlot()));
            ((TextView) view.findViewById(R.id.distance_from_current_location)).setText(String.valueOf(mParkingField.getEmptySlot()));
        }*/
    }

    @Override
    public void displayDistance(String distance) {
        ((TextView) getActivity().findViewById(R.id.distance_from_current_location)).setText(distance);
    }

    public List<String> createData(){
        data = new ArrayList<>();
        data.add("1 Giờ");
        data.add("2 Giờ");
        data.add("3 Giờ");
        data.add("4 Giờ");
        data.add("5 Giờ");
        data.add("6 Giờ");
        data.add("7 Giờ");
        data.add("8 Giờ");
        data.add("9 Giờ");
        return data;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
