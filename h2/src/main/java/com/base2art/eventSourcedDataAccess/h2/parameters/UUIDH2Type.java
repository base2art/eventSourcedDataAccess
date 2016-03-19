package com.base2art.eventSourcedDataAccess.h2.parameters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UUIDH2Type extends H2TypeBase<UUID> {
    public UUIDH2Type() {
        super("UUID");
    }

    @Override
    public UUID getValue(final ResultSet resultSet, final String pos) throws SQLException {
        return (UUID) resultSet.getObject(pos);
    }

    @Override
    public void setValue(final PreparedStatement preparedStatement, final int pos, final UUID val)
            throws SQLException {
        preparedStatement.setObject(pos, val);
    }
}
