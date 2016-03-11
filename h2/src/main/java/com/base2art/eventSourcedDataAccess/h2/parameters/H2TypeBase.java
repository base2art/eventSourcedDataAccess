package com.base2art.eventSourcedDataAccess.h2.parameters;

import lombok.Getter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class H2TypeBase<Type> implements H2Type<Type> {

    @Getter
    private final String typeName;

    public H2TypeBase(final String typeName) {
        this.typeName = typeName;
    }

    @SuppressWarnings("unchecked")
    public void setParameter(PreparedStatement statement, int position, Object value)
            throws SQLException {

        this.setValue(statement, position, (Type) value);
    }

    public Type getParameter(final ResultSet resultSet, final String position)
            throws SQLException {

        return this.getValue(resultSet, position);
    }

    @SuppressWarnings("unchecked")
    public <U> U getConvertedParameter(final ResultSet resultSet, final String position)
            throws SQLException {

        return (U) this.getValue(resultSet, position);
    }

    @Override
    public abstract Type getValue(final ResultSet resultSet, final String pos)
            throws SQLException;

    @Override
    public abstract void setValue(final PreparedStatement preparedStatement, final int pos, final Type val)
            throws SQLException;
}
