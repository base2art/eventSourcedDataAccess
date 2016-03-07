package com.base2art.eventSourcedDataAccess.h2.parameters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ByteArrayH2Type extends H2TypeBase<byte[]> {
    public ByteArrayH2Type() {
        super("BLOB", null, byte[].class);
    }

    @Override
    public byte[] getValue(final ResultSet resultSet, final String pos)
            throws SQLException {
        return resultSet.getBytes(pos);
    }

    @Override
    public void setValue(final PreparedStatement preparedStatement, final int pos, final byte[] val)
            throws SQLException {

        preparedStatement.setBytes(pos, val);
    }
}
