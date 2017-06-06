package com.tma.sparking.services.parkingfieldservice;

import com.tma.sparking.models.ParkingField;
import com.tma.sparking.services.parkingfieldservice.imp.ParkingFieldServiceImp;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Test ParkingFieldService class
 */
public class ParkingFieldEntryServiceTest {
    private List<ParkingField> actualResult = null;

    @Test
    public void find_ExistParkingField_NotNullParkingField() throws Exception {
        ParkingFieldService service = new ParkingFieldServiceImp();

        actualResult = service.findAll();

        Assert.assertNotEquals(0, actualResult.size());
    }

    @Test
    public void findOne_NotExistChannel_NullParkingField() throws Exception {
        ParkingFieldService service = new ParkingFieldServiceImp();

        actualResult = service.findAll();

        Assert.assertEquals(0, actualResult.size());
    }

    @Test
    public void findOne_NotExistField_NullParkingField() throws Exception {
        ParkingFieldService service = new ParkingFieldServiceImp();

        actualResult = service.findAll();

        Assert.assertEquals(0, actualResult.size());
    }
}