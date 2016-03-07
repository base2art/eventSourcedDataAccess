package com.base2art.eventSourcedDataAccess.h2.parameters;

import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
import lombok.Getter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class H2TypeBase<T> implements H2Type {

    @Getter
    private final String typeName;

    @Getter
    private final Class<T> primitiveClass;

    @Getter
    private final Class<T> nonPrimitiveClass;

    public H2TypeBase(final String typeName, final Class<T> primitiveClass, final Class<T> nonPrimitiveClass) {
        this.typeName = typeName;
        this.primitiveClass = primitiveClass;
        this.nonPrimitiveClass = nonPrimitiveClass;
    }

    @SuppressWarnings("unchecked")
    public void setParameter(PreparedStatement statement, int position, Object value)
            throws DataAccessReaderException {

        try {
            this.setValue(statement, position, (T) value);
        }
        catch (SQLException e) {
            throw new DataAccessReaderException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getParameter(final ResultSet resultSet, final String position)
            throws DataAccessReaderException {

        try {
            return (T) this.getValue(resultSet, position);
        }
        catch (SQLException e) {
            throw new DataAccessReaderException(e);
        }
    }

    public abstract T getValue(final ResultSet resultSet, final String pos)
            throws SQLException;

    public abstract void setValue(final PreparedStatement preparedStatement, final int pos, final T val)
            throws SQLException;
}
