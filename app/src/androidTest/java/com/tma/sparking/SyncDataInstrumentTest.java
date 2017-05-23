package com.tma.sparking;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.tma.sparking.services.http.Parking;
import com.tma.sparking.services.provider.ParkingProvider;
import com.tma.sparking.services.syncdata.SyncUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class SyncDataInstrumentTest {
    @Test
    public void testSyncData() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        Account account = SyncUtil.createSyncAccount(appContext);

        ContentResolver.requestSync(account, ParkingProvider.AUTHORITY, Bundle.EMPTY);
    }
}
