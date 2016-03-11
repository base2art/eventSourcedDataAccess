package com.base2art.eventSourcedDataAccess.h2.parameters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DateH2Type extends H2TypeBase<LocalDate> {
    public DateH2Type() {
        super("DATE");
    }

    @Override
    public LocalDate getValue(final ResultSet resultSet, final String pos)
            throws SQLException {
        return resultSet.getDate(pos).toLocalDate();
    }

    @Override
    public void setValue(final PreparedStatement preparedStatement, final int pos, final LocalDate val)
            throws SQLException {
        preparedStatement.setDate(pos, java.sql.Date.valueOf(val));
    }
}
