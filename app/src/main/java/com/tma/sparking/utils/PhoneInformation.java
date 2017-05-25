package com.tma.sparking.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

public class PhoneInformation {
    public static String getPhoneNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number();
    }
}
