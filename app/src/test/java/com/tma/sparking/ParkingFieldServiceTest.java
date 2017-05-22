package com.tma.sparking;

import com.tma.sparking.models.ParkingField;
import com.tma.sparking.services.ParkingFieldService;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ParkingFieldServiceTest {
    private ParkingField actualResult = null;

    @Test
    public void findOne_Success() throws Exception {
        final CountDownLatch lock = new CountDownLatch(1);

        ParkingFieldService service = new ParkingFieldService();
        service.addOnFetchResultListener(new ParkingFieldService.OnFetchResult() {
            @Override
            public void onSuccess(ParkingField parkingField) {
                lock.countDown();
                actualResult = parkingField;
            }

            @Override
            public void onFailure(Throwable t) {
                lock.countDown();
            }
        });
        service.findOne(270768, 2);

        lock.await(1000000, TimeUnit.MILLISECONDS);

        Assert.assertNotNull(actualResult);
        Assert.assertEquals(270768, actualResult.getChannel().getId(), 0L);
        Assert.assertEquals(2, actualResult.getId());
    }

    @Test
    public void findOne_ChannelNotFound() throws Exception {
        final CountDownLatch lock = new CountDownLatch(1);

        actualResult = new ParkingField();
        ParkingFieldService service = new ParkingFieldService();
        service.addOnFetchResultListener(new ParkingFieldService.OnFetchResult() {
            @Override
            public void onSuccess(ParkingField parkingField) {
                lock.countDown();
                actualResult = parkingField;
            }

            @Override
            public void onFailure(Throwable t) {
                lock.countDown();
            }
        });
        service.findOne(1, 2);

        lock.await(1000000, TimeUnit.MILLISECONDS);

        Assert.assertNull(actualResult);
    }

    @Test
    public void findOne_FieldNotFound() throws Exception {
        final CountDownLatch lock = new CountDownLatch(1);

        ParkingFieldService service = new ParkingFieldService();
        service.addOnFetchResultListener(new ParkingFieldService.OnFetchResult() {
            @Override
            public void onSuccess(ParkingField parkingField) {
                lock.countDown();
                actualResult = parkingField;
            }

            @Override
            public void onFailure(Throwable t) {
                lock.countDown();
            }
        });
        service.findOne(270768, 9);

        lock.await(1000000, TimeUnit.MILLISECONDS);

        Assert.assertNotNull(actualResult);
        Assert.assertEquals(270768, actualResult.getChannel().getId(), 0L);
        Assert.assertEquals(-1, actualResult.getId());
        Assert.assertNull(actualResult.getName());
        Assert.assertEquals(0, actualResult.getEmptySlot());
        Assert.assertEquals(0, actualResult.getTotalSlot());
    }
}