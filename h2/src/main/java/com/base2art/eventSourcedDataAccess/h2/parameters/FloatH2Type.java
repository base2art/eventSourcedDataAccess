package com.base2art.eventSourcedDataAccess.h2.parameters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FloatH2Type extends H2TypeBase<Float> {
    public FloatH2Type() {
        super("REAL");
    }

    @Override
    public Float getValue(final ResultSet resultSet, final String pos)
            throws SQLException {
        return resultSet.getFloat(pos);
    }

    @Override
    public void setValue(final PreparedStatement preparedStatement, final int pos, final Float val)
            throws SQLException {
        preparedStatement.setFloat(pos, val);
    }
}
