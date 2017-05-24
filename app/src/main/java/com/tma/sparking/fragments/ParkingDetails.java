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

/**
 * Created by ntmhanh on 5/23/2017.
 */

public class ParkingDetails extends Fragment {
    private TextView tvTitle;
    private TextView tvDescription;

    public ParkingDetails() {
    }

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
        /*tvTitle = (TextView)getView().findViewById(R.id.makerTitle);
        tvDescription = (TextView)getView().findViewById(R.id.makerDescription);*/
      /*  Bundle bundle = getArguments();
        String title = bundle.getString("markerTitle");
        String description = bundle.getString("makerDescriptions");
        this.tvTitle.setText(title);
        this.tvDescription.setText(description);*/

    }
}
