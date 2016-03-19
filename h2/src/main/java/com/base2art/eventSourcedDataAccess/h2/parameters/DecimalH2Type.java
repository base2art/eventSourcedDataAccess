package com.base2art.eventSourcedDataAccess.h2.parameters;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DecimalH2Type extends H2TypeBase<BigDecimal> {
    public DecimalH2Type() {
        super("DECIMAL");
    }

    @Override
    public BigDecimal getValue(final ResultSet resultSet, final String pos) throws SQLException {
        return resultSet.getBigDecimal(pos);
    }

    @Override
    public void setValue(final PreparedStatement preparedStatement, final int pos, final BigDecimal val) throws SQLException {
        preparedStatement.setBigDecimal(pos, val);
    }
}
