package com.tma.sparking.robolectric;

import com.tma.sparking.BuildConfig;
import com.tma.sparking.LoginActivity;
import com.tma.sparking.SplashScreenActivity;
import com.tma.sparking.utils.Constants;
import com.tma.sparking.utils.SharedPreferenceUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLooper;

import static junit.framework.Assert.assertNotNull;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by lnthao on 5/31/2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SplashScreenLoginMainNavigationTest {
    @Test
    public void launchApp_userDidNotLog_goToLoginScreen(){
        // Arrange
        SharedPreferenceUtils.putBoolean(Constants.LOGGED, false);
        SplashScreenActivity splashScreenActivity = Robolectric.setupActivity(SplashScreenActivity.class);
        assertNotNull(splashScreenActivity);
        // Wait for postDelay
        ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
        // Assert
        assertThat(shadowOf(splashScreenActivity).getNextStartedActivity()
                .getComponent().getClassName()).isEqualTo(LoginActivity.class.getName());
    }
}
