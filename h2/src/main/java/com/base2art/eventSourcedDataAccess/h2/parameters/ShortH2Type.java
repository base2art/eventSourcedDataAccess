package com.base2art.eventSourcedDataAccess.h2.parameters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShortH2Type extends H2TypeBase<Short> {
    public ShortH2Type() {
        super("SMALLINT");
    }

    @Override
    public Short getValue(final ResultSet resultSet, final String pos)
            throws SQLException {
        return resultSet.getShort(pos);
    }

    @Override
    public void setValue(final PreparedStatement preparedStatement, final int pos, final Short val)
            throws SQLException {
        preparedStatement.setShort(pos, val);
    }
}
