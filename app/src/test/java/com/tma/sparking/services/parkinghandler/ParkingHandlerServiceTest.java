package com.tma.sparking.services.parkinghandler;

import com.tma.sparking.BuildConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ServiceController;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by pkimhuy on 6/6/2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ParkingHandlerServiceTest {
    private ServiceController<ParkingHandlerService> mController;
    private ParkingHandlerService mParkingHandlerService;

    @Before
    public void setup() {
        mController = Robolectric.buildService(ParkingHandlerService.class);
        mParkingHandlerService = mController.get();
    }

    @Test
    public void startService_callRunnable() throws Exception {
        Runnable mockTask = mock(Runnable.class);
        mParkingHandlerService.setParkingTask(mockTask);
        mParkingHandlerService.setDelayMillis(2000);

        mController.startCommand(0, 0);

        Thread.sleep(5000);

        verify(mockTask, atLeast(1)).run();
    }

    @After
    public void tearDown() {
        mController.destroy();
    }
}
