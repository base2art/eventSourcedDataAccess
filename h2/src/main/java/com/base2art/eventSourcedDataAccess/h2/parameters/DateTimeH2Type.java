package com.base2art.eventSourcedDataAccess.h2.parameters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class DateTimeH2Type extends H2TypeBase<Instant> {
    public DateTimeH2Type() {
        super("TIMESTAMP", null, Instant.class);
    }

    @Override
    public Instant getValue(final ResultSet resultSet, final String pos)
            throws SQLException {
        return resultSet.getTimestamp(pos).toInstant();
    }

    @Override
    public void setValue(final PreparedStatement preparedStatement, final int pos, final Instant val)
            throws SQLException {
        preparedStatement.setTimestamp(pos, java.sql.Timestamp.from(val));
    }
}
