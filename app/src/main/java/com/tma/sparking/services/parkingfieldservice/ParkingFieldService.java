package com.tma.sparking.services.parkingfieldservice;

import com.tma.sparking.models.ParkingField;

import java.util.List;

/**
 * Service for reading data from server
 */
public interface ParkingFieldService {
    /**
     * Get all parking fields from web service
     *
     * @return a list of parking fields
     */
    List<ParkingField> findAll();
}
