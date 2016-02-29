package com.base2art.eventSourcedDataAccess;

import java.util.stream.Stream;

public interface FilteredPagedDataAccessReader<Id, ObjectEntity, FilterOptions, OrderOptions> {

    Stream<ObjectEntity> streamFilteredAndPaged(
            FilterOptions filterOptions,
            OrderOptions orderOptions,
            Id marker,
            int pageSize)
            throws DataAccessReaderException;

    ObjectEntity[] getFilteredAndPaged(
            FilterOptions filterOptions,
            OrderOptions orderOptions,
            Id marker,
            int pageSize)
            throws DataAccessReaderException;
}
