package com.base2art.eventSourcedDataAccess;

import java.util.stream.Stream;

public interface PagedDataAccessReader<Id, ObjectEntity, OrderOptions> {

    Stream<ObjectEntity> streamPaged(OrderOptions options, Id marker, int pageSize)
            throws DataAccessReaderException;

    ObjectEntity[] getPaged(OrderOptions options, Id marker, int pageSize)
            throws DataAccessReaderException;
}
