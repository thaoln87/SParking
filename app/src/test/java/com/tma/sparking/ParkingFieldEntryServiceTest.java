package com.tma.sparking;

import com.tma.sparking.models.ParkingField;
import com.tma.sparking.services.http.ParkingFieldService;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test ParkingFieldService class
 */
public class ParkingFieldEntryServiceTest {
    private ParkingField actualResult = null;

    @Test
    public void findOne_ExistParkingField_NotNullParkingField() throws Exception {
        ParkingFieldService service = new ParkingFieldService();
        actualResult = service.findOne(270768, 2);

        Assert.assertNotNull(actualResult);
    }

    @Test
    public void findOne_NotExistChannel_NullParkingField() throws Exception {
        ParkingFieldService service = new ParkingFieldService();
        actualResult = service.findOne(1, 2);
        Assert.assertNull(actualResult);
    }

    @Test
    public void findOne_NotExistField_NullParkingField() throws Exception {
        ParkingFieldService service = new ParkingFieldService();
        actualResult = service.findOne(270768, 9);
        Assert.assertNull(actualResult);
    }
}