package com.tma.sparking.services.syncdata;

import android.content.Context;
import android.content.Intent;

/**
 * Created by jinich on 5/25/2017.
 */

public class DataPolling {
    public void startPollingService(Context context) {
        Intent intent = new Intent(context, AlarmService.class);
        context.startService(intent);
    }
}
