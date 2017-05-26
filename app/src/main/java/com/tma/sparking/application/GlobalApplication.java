package com.tma.sparking.application;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.tma.sparking.utils.SharedPreferenceUtils;

/**
 * Created by lnthao on 5/25/2017.
 */

public class GlobalApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferenceUtils.getInstance().init(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
