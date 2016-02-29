package com.base2art.eventSourcedDataAccess;

public interface ItemDataAccessReader<Id, ObjectEntity> {

    ObjectEntity getItem(Id id) throws DataAccessReaderException;
}
