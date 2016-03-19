package com.base2art.eventSourcedDataAccess.h2.parameters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface RawH2Type {

    String getTypeName();

    void setParameter(PreparedStatement statement, int pos, Object object)
            throws SQLException;

    Object getParameter(final ResultSet resultSet, final String position)
            throws SQLException;

    <T> T getConvertedParameter(final ResultSet resultSet, final String position)
            throws SQLException;
}
