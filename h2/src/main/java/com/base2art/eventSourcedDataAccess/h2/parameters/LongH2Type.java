package com.base2art.eventSourcedDataAccess.h2.parameters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LongH2Type extends H2TypeBase<Long> {
    public LongH2Type() {
        super("BIGINT", long.class, Long.class);
    }

    @Override
    public Long getValue(final ResultSet resultSet, final String pos) throws SQLException {
        return resultSet.getLong(pos);
    }

    @Override
    public void setValue(final PreparedStatement preparedStatement, final int pos, final Long val) throws SQLException {
        preparedStatement.setLong(pos, val);
    }
}
