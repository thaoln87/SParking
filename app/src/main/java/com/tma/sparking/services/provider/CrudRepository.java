package com.tma.sparking.services.provider;

/**
 * Define method for CRUD operation on database entity
 */
public interface CrudRepository<E> {
    /**
     * Insert an entity into database table
     *
     * @param entity Entity to insert into database
     * @return id of inserted entity
     */
    long insert(E entity);

    /**
     * Find an entity by id
     *
     * @param id of the entity we want to find
     * @return an entity
     */
    E findOne(long id);

    /**
     * Delete an entity from database table
     *
     * @param id of the entity to be deleted
     */
    void delete(long id);
}
