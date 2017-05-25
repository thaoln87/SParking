package com.tma.sparking.services.syncdata;

import android.accounts.Account;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Toast;

import com.tma.sparking.services.provider.ParkingProvider;

public class Alarm extends BroadcastReceiver {
    private long mTriggerInterval;

    public Alarm() {
        super();

        mTriggerInterval = 20000;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager powerManager = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wakeLock.acquire();

        SyncDataManager syncDataManager = new SyncDataManager(context);
        syncDataManager.requestSync();

        setAlarm(context);
        wakeLock.release();
    }

    /**
     * Setup alarm
     *
     * @param context application context
     */
    public void setAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + mTriggerInterval, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + mTriggerInterval, pendingIntent);
        }
    }

    /**
     * Cancel alarm
     *
     * @param context application context
     */
    public void cancelAlarm(Context context) {
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void setTriggerInterval(long triggerInterval) {
        mTriggerInterval = triggerInterval;
    }
}
