package com.base2art.eventSourcedDataAccess.h2.parameters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjectH2Type extends H2TypeBase<Object> {

    public ObjectH2Type() {
        super("OTHER");
    }

    @Override
    public Object getValue(final ResultSet resultSet, final String pos) throws SQLException {
        return resultSet.getObject(pos);
    }

    @Override
    public void setValue(final PreparedStatement preparedStatement, final int pos, final Object val) throws SQLException {

        preparedStatement.setObject(pos, val);
    }
}
