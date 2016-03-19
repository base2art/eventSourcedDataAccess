package com.base2art.eventSourcedDataAccess.h2.parameters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BooleanH2Type extends H2TypeBase<Boolean> {
    public BooleanH2Type() {
        super("BOOLEAN");
    }

    @Override
    public Boolean getValue(final ResultSet resultSet, final String pos) throws SQLException {

        return resultSet.getBoolean(pos);
    }

    @Override
    public void setValue(final PreparedStatement preparedStatement, final int pos, final Boolean val) throws SQLException {

        preparedStatement.setBoolean(pos, (boolean) val);
    }
}
