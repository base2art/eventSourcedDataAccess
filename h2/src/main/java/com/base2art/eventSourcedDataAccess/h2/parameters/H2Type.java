package com.base2art.eventSourcedDataAccess.h2.parameters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface H2Type<T> extends RawH2Type {

    void setValue(PreparedStatement statement, int pos, T object)
            throws SQLException;

    T getValue(final ResultSet resultSet, final String position)
            throws SQLException;
}
