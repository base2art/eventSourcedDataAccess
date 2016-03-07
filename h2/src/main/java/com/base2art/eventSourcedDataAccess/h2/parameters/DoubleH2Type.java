package com.base2art.eventSourcedDataAccess.h2.parameters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoubleH2Type extends H2TypeBase<Double> {
    public DoubleH2Type() {
        super("DOUBLE", double.class, Double.class);
    }

    @Override
    public Double getValue(final ResultSet resultSet, final String pos) throws SQLException {
        return resultSet.getDouble(pos);
    }

    @Override
    public void setValue(final PreparedStatement preparedStatement, final int pos, final Double val) throws SQLException {
        preparedStatement.setDouble(pos, val);
    }
}
