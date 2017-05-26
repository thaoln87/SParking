package com.tma.sparking.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

/**
 * Get phone number by using methods defined in this class
 */
public class PhoneInformation {
    public static final int REQUEST_READ_PHONE_STATE = 1;

    private Activity mActivity;

    public PhoneInformation(Activity activity) {
        mActivity = activity;
    }

    /**
     * Activity implement onRequestPermissionsResult should delegate all the work to this method
     *
     * @param requestCode REQUEST_READ_PHONE_STATE request code
     * @param permissions List of permissions
     * @param grantResults List of grant results, PERMISSION_GRANTED or PERMISSION_DENIED
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PhoneInformation.REQUEST_READ_PHONE_STATE) {
            if (permissions.length == 1 & grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted();
            }
        }
    }

    /**
     * Get phone number of phone's   owner, this operation require READ_PHONE_STATE permission
     * Because this is a dangerous permission, for API level 23 and above, we need to request this permission at runtime
     */
    public void getPhoneNumber() {
        // Request READ_PHONE_STATE permission (for API level 23 and above)

        // Check if user whether or not have this permission
        int status = ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_PHONE_STATE);
        if (status == PackageManager.PERMISSION_GRANTED) {
            onPermissionGranted();
        } else {
            // Request permission
            requestReadPhoneStatePermission(mActivity);
        }
    }

    private void onPermissionGranted() {
        OnPhoneNumberAvailable onPhoneNumberAvailable = (OnPhoneNumberAvailable)mActivity;
        String phoneNumber = getPhoneNumberOfUser(mActivity);
        onPhoneNumberAvailable.onPhoneNumberAvailable(phoneNumber);
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
