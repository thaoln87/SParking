package com.tma.sparking.robolectric;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.tma.sparking.BuildConfig;
import com.tma.sparking.MainActivity;
import com.tma.sparking.R;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by ntmhanh on 5/30/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class NavigationOnMainActivityRobolectricTest {
    private MainActivity activity;
    private NavigationView navigationView;
    @Before
    public void init() {
        activity = Robolectric.setupActivity(MainActivity.class);
        navigationView = (NavigationView)activity.findViewById(R.id.nav_view);
    }


    @Test
    public void checkDrawerNavigation_returnNotNullAndSizeOfMenuIs4(){
        Menu menu = navigationView.getMenu();
        Assert.assertNotNull(menu);
        Assert.assertEquals(4, menu.size());

    }
    @Test
    public void checkFirstItem_returnTitleIsMaps(){
        Menu menu = navigationView.getMenu();
        assertThat(menu.findItem(R.id.nav_map).getTitle().toString(), equalTo("Maps"));

    }
    @Test
    public void selectedOnFirstItem_returnOpenedMapsFragmentNotNull(){
        MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_map);
        activity.onNavigationItemSelected(menuItem);
        Fragment fragment = activity.getSupportFragmentManager().getFragments().get(0);
        assertNotNull(fragment);
    }
}
