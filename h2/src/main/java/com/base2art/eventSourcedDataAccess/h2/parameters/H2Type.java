package com.base2art.eventSourcedDataAccess.h2.parameters;

import com.base2art.eventSourcedDataAccess.DataAccessReaderException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface H2Type {
    Class<?> getPrimitiveClass();

    Class<?> getNonPrimitiveClass();

    String getTypeName();

    void setParameter(PreparedStatement statement, int pos, Object object)
            throws DataAccessReaderException;

    <T> T getParameter(final ResultSet resultSet, final String position)
            throws DataAccessReaderException;
}
