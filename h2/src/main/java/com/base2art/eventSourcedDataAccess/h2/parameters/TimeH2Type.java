package com.base2art.eventSourcedDataAccess.h2.parameters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public class TimeH2Type extends H2TypeBase<LocalTime> {
    public TimeH2Type() {
        super("TIME", null, LocalTime.class);
    }

    @Override
    public LocalTime getValue(final ResultSet resultSet, final String pos)
            throws SQLException {
        return resultSet.getTime(pos).toLocalTime();
    }

    @Override
    public void setValue(final PreparedStatement preparedStatement, final int pos, final LocalTime val)
            throws SQLException {
        preparedStatement.setTime(pos, java.sql.Time.valueOf(val));
    }
}
