package com.tma.sparking;

import android.accounts.Account;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tma.sparking.fragments.MapsFragment;
import com.tma.sparking.interfaces.NavigationDrawerCallbacks;
import com.tma.sparking.services.provider.ParkingContract;
import com.tma.sparking.services.provider.ParkingProvider;
import com.tma.sparking.services.syncdata.SyncAdapter;
import com.tma.sparking.services.syncdata.SyncUtil;

public class MainActivity extends FragmentActivity
        implements NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Show drawer navigation with full height
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DrawerLayout drawer = (DrawerLayout) inflater.inflate(R.layout.decor, null);
        // Steal the first child of decor view
        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        View child = decor.getChildAt(0);
        decor.removeView(child);
        FrameLayout container = (FrameLayout) drawer.findViewById(R.id.container); // This is the container we defined just now.
        container.addView(child);
        // Make the drawer replace the first child
        decor.addView(drawer);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        Context appContext = getApplicationContext();

        Account account = SyncUtil.createSyncAccount(appContext);

        Bundle extras = new Bundle();
        extras.putLong(SyncAdapter.KEY_CHANNEL_ID, 270768);
        extras.putInt(SyncAdapter.KEY_FIELD_ID, 2);
        ContentResolver.requestSync(account, ParkingProvider.AUTHORITY, extras);

        ContentResolver contentResolver = appContext.getContentResolver();
        Uri uri = ParkingContract.ParkingFieldEntry.CONTENT_URI;
        String channelIdColumn = ParkingContract.ParkingFieldEntry.COLUMN_NAME_CHANNEL_ID;
        String parkingFieldNumberColumn = ParkingContract.ParkingFieldEntry.COLUMN_NAME_PARKING_FIELD_NUMBER;
        String selection = channelIdColumn + " = ? AND " + parkingFieldNumberColumn + " = ?";
        String[] selectionArgs = { String.valueOf(270768), String.valueOf(2) };
        Cursor cursor = contentResolver.query(uri, null, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(ParkingContract.ParkingFieldEntry.COLUMN_NAME_CHANNEL_ID));
            Log.d("abc", String.valueOf(id));
        } else {
            Log.d("abc", "dd");
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new MapsFragment();
                break;
            case 1:
                Toast.makeText(this, "This is Manage Cars fragment", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "This is Payment fragment", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, "This is Promotion fragment", Toast.LENGTH_SHORT).show();
            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit();
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_maps, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
