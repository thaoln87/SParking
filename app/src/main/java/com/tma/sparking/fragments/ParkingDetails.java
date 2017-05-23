package com.tma.sparking.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.parking_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTitle = (TextView)getView().findViewById(R.id.makerTitle);
        tvDescription = (TextView)getView().findViewById(R.id.makerDescription);
        Bundle bundle = getArguments();
        String title = bundle.getString("markerTitle");
        String description = bundle.getString("makerDescriptions");
        this.tvTitle.setText(title);
        this.tvDescription.setText(description);

    }
}
