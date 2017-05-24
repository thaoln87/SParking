package com.tma.sparking.services.syncdata;

import android.accounts.Account;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Toast;

import com.tma.sparking.services.provider.ParkingProvider;

/**
 * Created by jinich on 5/25/2017.
 */

public class Alarm extends BroadcastReceiver {
    public Alarm() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager powerManager = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wakeLock.acquire();

        Account account = SyncUtil.createSyncAccount(context);
        Bundle extras = new Bundle();
        extras.putLong(SyncAdapter.KEY_CHANNEL_ID, 270768);
        ContentResolver.setSyncAutomatically(account, ParkingProvider.AUTHORITY, true);
        ContentResolver.setMasterSyncAutomatically(true);
        ContentResolver.requestSync(account, ParkingProvider.AUTHORITY, extras);

        setAlarm(context);
        wakeLock.release();
    }

    public void setAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, 4000, pendingIntent);
    }

    public void cancelAlarm(Context context) {
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
