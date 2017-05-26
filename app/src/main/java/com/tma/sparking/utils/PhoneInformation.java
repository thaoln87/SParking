package com.tma.sparking.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

public abstract class PhoneInformation implements ActivityCompat.OnRequestPermissionsResultCallback {
    public static final int REQUEST_READ_PHONE_STATE = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PhoneInformation.REQUEST_READ_PHONE_STATE) {
            if (permissions.length == 1 & grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String phoneNumber = getPhoneNumber(getActivity());
                doWork(phoneNumber);
            }
        }
    }

    public abstract void doWork(String phoneNumber);
    public abstract Activity getActivity();

    /**
     * Get phone number of phone's   owner, this operation require READ_PHONE_STATE permission
     * Because this is a dangerous permission, for API level 23 and above, we need to request this permission at runtime
     *
     * @param activity Activity that directly call this method or contain fragment call this method
     * @return Phone number of the owner in String, return null if error happened
     */
    public String getPhoneNumber(Activity activity) {
        // Request READ_PHONE_STATE permission (for API level 23 and above)

        String phoneNumber = null;
        // Check if user whether or not have this permission
        int status = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        if (status == PackageManager.PERMISSION_GRANTED) {
            phoneNumber = getPhoneNumberOfUser(activity);
            doWork(phoneNumber);
        } else {
            // Request permission
            requestReadPhoneStatePermission(activity);
        }

        return phoneNumber;
    }

    private String getPhoneNumberOfUser(Activity activity) {
        TelephonyManager telephonyManager = (TelephonyManager)activity.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number();
    }

    private void requestReadPhoneStatePermission(Activity activity) {
        String[] permissions = { Manifest.permission.READ_PHONE_STATE };
        ActivityCompat.requestPermissions(activity, permissions, REQUEST_READ_PHONE_STATE);
    }
}
