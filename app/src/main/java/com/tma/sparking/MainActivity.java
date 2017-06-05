package com.tma.sparking;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tma.sparking.fragments.MapsFragment;
import com.tma.sparking.models.ParkingField;
import com.tma.sparking.services.provider.ParkingContract;
import com.tma.sparking.services.provider.ParkingFieldDataBuilder;
import com.tma.sparking.utils.OnPhoneNumberAvailable;
import com.tma.sparking.utils.PhoneInformation;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {
    private MapsFragment mapFragment;
    private ActionBarDrawerToggle mToggle;
    private List<ParkingField> mParkingFields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(mToggle);
        mToggle.syncState();

        mToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                                                              MainActivity.super.onBackPressed();
                                                          }
                                                      }
                                                  }
        );

        mapFragment = new MapsFragment();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // select map item
        navigationView.setCheckedItem(R.id.nav_map);
        navigationView.getMenu().performIdentifierAction(R.id.nav_map, 0);

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                setNavIcon();
            }
        });
    }

    private void setNavIcon() {
        mToggle.setDrawerIndicatorEnabled(getSupportFragmentManager().getBackStackEntryCount() == 0);
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            getSupportActionBar().setTitle(R.string.app_name);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_map) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {
                super.onBackPressed();
            } else {
                fragmentManager.beginTransaction().replace(R.id.content_main, mapFragment).commit();
            }
        } else if (id == R.id.nav_manage_cars) {

        } else if (id == R.id.nav_payment) {

        } else if (id == R.id.nav_promotion) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == MapsFragment.MY_PERMISSIONS_REQUEST_LOCATION) {
            mapFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = ParkingContract.ParkingFieldEntry.CONTENT_URI;
        CursorLoader cursorLoader = new CursorLoader(this, uri, null, null, null, null);
        cursorLoader.setUpdateThrottle(5000);
        return cursorLoader;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        loader.reset();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        List<ParkingField> parkingFields = new ArrayList<>();

        while (cursor.moveToNext()) {
            ParkingField parkingField = ParkingFieldDataBuilder.createFromCursor(cursor);
            parkingFields.add(parkingField);
        }

        if (parkingFields.size() > 0) {
            mParkingFields = parkingFields;
        }
    }
}
