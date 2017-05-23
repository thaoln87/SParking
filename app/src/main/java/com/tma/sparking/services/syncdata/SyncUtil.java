package com.tma.sparking.services.syncdata;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

/**
 * Define some helper method for sync adapter framework
 */
public class SyncUtil {
    public static final String ACCOUNT_NAME = "default_account";
    public static final String ACCOUNT_TYPE = "stub";

    /**
     * Create a new account
     *
     * @param context Application context
     * @return
     */
    public static Account createSyncAccount(Context context) {
        Account account = new Account(ACCOUNT_NAME, ACCOUNT_TYPE);
        AccountManager accountManager = (AccountManager)context.getSystemService(Context.ACCOUNT_SERVICE);
        accountManager.addAccountExplicitly(account, null, null);
        return account;
    }
}
