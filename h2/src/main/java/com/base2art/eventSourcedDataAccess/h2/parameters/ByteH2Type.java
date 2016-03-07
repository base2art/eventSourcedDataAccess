package com.base2art.eventSourcedDataAccess.h2.parameters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ByteH2Type extends H2TypeBase<Byte> {
    public ByteH2Type() {
        super("TINYINT", byte.class, Byte.class);
    }

    @Override
    public Byte getValue(final ResultSet resultSet, final String pos) throws SQLException {

        return resultSet.getByte(pos);
    }

    @Override
    public void setValue(final PreparedStatement preparedStatement, final int pos, final Byte val) throws SQLException {

        preparedStatement.setByte(pos, val);
    }
}
