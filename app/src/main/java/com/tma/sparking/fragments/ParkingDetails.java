package com.tma.sparking.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tma.sparking.R;
import com.tma.sparking.models.ParkingField;
import com.tma.sparking.utils.GoogleMapUtils;

/**
 * Created by ntmhanh on 5/23/2017.
 */

public class ParkingDetails extends Fragment {
    private TextView tvTitle;
    private TextView tvDescription;

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

        //set toolbar appearance
        //toolbar.setBackground(R.color.material_blue_grey_800);

        //for crate home button
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setHomeButtonEnabled(true);
        activity.getSupportActionBar().setTitle("Điểm đỗ 103");
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        ParkingField parkingField= bundle.getParcelable("parkingField");

        if (parkingField != null) {
            GoogleMapUtils googleMapUtils = new GoogleMapUtils(getContext());
            String parkingAddress = googleMapUtils.getCompleteAddress(parkingField.getLatitude(), parkingField.getLongitude());
            ((TextView) getActivity().findViewById(R.id.parking_name)).setText(parkingField.getName());
            ((TextView) getActivity().findViewById(R.id.parking_address)).setText(parkingAddress);
        }


    }
}
