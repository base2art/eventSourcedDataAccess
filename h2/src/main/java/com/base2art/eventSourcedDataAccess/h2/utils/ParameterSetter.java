package com.base2art.eventSourcedDataAccess.h2.utils;

import com.base2art.eventSourcedDataAccess.DataAccessReaderException;

import java.sql.PreparedStatement;

@FunctionalInterface
public interface ParameterSetter {
    void accept(PreparedStatement statement) throws DataAccessReaderException;
}
