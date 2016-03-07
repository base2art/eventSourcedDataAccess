package com.base2art.eventSourcedDataAccess.h2.parameters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntH2Type extends H2TypeBase<Integer> {
    public IntH2Type() {
        super("INT", int.class, Integer.class);
    }

    @Override
    public Integer getValue(final ResultSet resultSet, final String pos)
            throws SQLException {
        return resultSet.getInt(pos);
    }

    @Override
    public void setValue(final PreparedStatement preparedStatement, final int pos, final Integer val)
            throws SQLException {
        preparedStatement.setInt(pos, val);
    }
}
