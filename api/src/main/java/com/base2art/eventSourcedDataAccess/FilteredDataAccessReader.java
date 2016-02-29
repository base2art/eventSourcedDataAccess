package com.base2art.eventSourcedDataAccess;

import java.util.stream.Stream;

public interface FilteredDataAccessReader<ObjectEntity, FilterOptions> {

    Stream<ObjectEntity> streamFiltered(FilterOptions options)
            throws DataAccessReaderException;

    ObjectEntity[] getFiltered(FilterOptions options)
            throws DataAccessReaderException;
}
