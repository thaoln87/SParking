package com.tma.sparking.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by lnthao on 5/25/2017.
 */

public class SharedPreferenceUtils {

    private static final SharedPreferenceUtils instance = new SharedPreferenceUtils();
    private static final String APP_NAME = "SParking";
    private static SharedPreferences settingPreferences;

    public static SharedPreferenceUtils getInstance() {
        return instance;
    }

    public static void put(String key, String value) {
        SharedPreferences.Editor editor = settingPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void putInt(String key, int value) {
        SharedPreferences.Editor editor = settingPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }


    public static void putFloat(String key, float value) {
        SharedPreferences.Editor editor = settingPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = settingPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void putLong(String key, long value) {
        SharedPreferences.Editor editor = settingPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static String getString(String key, String defaultValue) {
        return settingPreferences.getString(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        return settingPreferences.getInt(key, defaultValue);
    }

    public static float getFloat(String key, float defaultValue) {
        return settingPreferences.getFloat(key, defaultValue);
    }

    @SuppressWarnings("SameParameterValue")
    public static boolean getBoolean(String key, boolean defaultValue) {
        return settingPreferences.getBoolean(key, defaultValue);
    }

    public static long getLong(String key, long defaultValue) {
        return settingPreferences.getLong(key, defaultValue);
    }

    public static void remove(String key) {
        settingPreferences.edit().remove(key).apply();
    }

    public static void clearAll() {

        Set<String> keySet = settingPreferences.getAll().keySet();

        SharedPreferences.Editor editor = settingPreferences.edit();
        for (String deleteKey : keySet) {
            editor.remove(deleteKey).apply();
        }
    }

    public void init(Context context) {
        if (settingPreferences == null)
            settingPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
    }

}