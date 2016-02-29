package com.base2art.eventSourcedDataAccess;

import java.util.stream.Stream;

public interface DataAccessReader<ObjectEntity> {

    Stream<ObjectEntity> stream() throws DataAccessReaderException;

    ObjectEntity[] get() throws DataAccessReaderException;
}
