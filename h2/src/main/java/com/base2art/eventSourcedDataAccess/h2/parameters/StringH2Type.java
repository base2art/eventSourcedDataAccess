package com.base2art.eventSourcedDataAccess.h2.parameters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StringH2Type extends H2TypeBase<String> {
    public StringH2Type() {
        super("VARCHAR_IGNORECASE(MAX)", null, String.class);
    }

    @Override
    public String getValue(final ResultSet resultSet, final String pos)
            throws SQLException {
        return resultSet.getString(pos);
    }

    @Override
    public void setValue(final PreparedStatement preparedStatement, final int pos, final String val)
            throws SQLException {
        preparedStatement.setString(pos, val);
    }
}
