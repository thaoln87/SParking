package com.tma.sparking.services.provider;

import com.tma.sparking.models.ParkingField;

/**
 * CRUD repository for parking_field table
 */
public class ParkingFieldRepository implements CrudRepository<ParkingField> {
    @Override
    public ParkingField findOne(long id) {
        return null;
    }

    @Override
    public long insert(ParkingField entity) {
        return 0;
    }

    @Override
    public void delete(long id) {

    }
}
