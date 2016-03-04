package com.base2art.eventSourcedDataAccess.h2;

import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
import com.base2art.eventSourcedDataAccess.DataAccessWriterException;
import com.base2art.eventSourcedDataAccess.h2.utils.SqlGetter;
import com.base2art.eventSourcedDataAccess.h2.utils.SqlSetter;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public enum H2Type {
    Int("INT", int.class, Integer.class, H2Type::setInt, H2Type::getInt),
    Boolean("BOOLEAN", boolean.class, Boolean.class, H2Type::setBoolean, H2Type::getBoolean),
    Byte("TINYINT", byte.class, Byte.class, H2Type::setByte, H2Type::getByte),
    SmallInt("SMALLINT", short.class, Short.class, H2Type::setShort, H2Type::getShort),
    BigInt("BIGINT", long.class, Long.class, H2Type::setLong, H2Type::getLong),
    Decimal("DECIMAL", null, BigDecimal.class, H2Type::setBigDecimal, H2Type::getBigDecimal),
    Double("DOUBLE", double.class, Double.class, H2Type::setDouble, H2Type::getDouble),
    Real("REAL", float.class, Float.class, H2Type::setFloat, H2Type::getFloat),
    Time("TIME", null, java.sql.Time.class, H2Type::setTime, H2Type::getTime),
    Date("DATE", null, java.sql.Date.class, H2Type::setDate, H2Type::getDate),
    TimeStamp("TIMESTAMP", null, java.util.Date.class, H2Type::setTimestamp, H2Type::getTimestamp),
    Other("OTHER", null, null, H2Type::setObject, H2Type::getObject),
    Text("VARCHAR_IGNORECASE(MAX)", null, String.class, H2Type::setString, H2Type::getString),
    BINARY("BLOB", null, byte[].class, H2Type::setBytes, H2Type::getBytes),
    UUID("UUID", null, java.util.UUID.class, H2Type::setUUID, H2Type::getUUID);

//    Identity("IDENTITY", long.class, Long.class, (stmt, pos, val) -> stmt.setBoolean(pos, (boolean)val)),
    //    BINARY("BINARY", null, byte[].class, (stmt, pos, val) -> stmt.setBoolean(pos, (boolean)val)),
    //    Document("VARCHAR_IGNORECASE(MAX)", null, java.util.Date.class),


    private final String typeName;
    private final Class<?> primitiveClass;
    private final Class<?> nonPrimitiveClass;
    private final SqlSetter setParam;
    private final SqlGetter getParam;

    H2Type(
            final String typeName,
            final Class<?> primitiveClass,
            final Class<?> nonPrimitiveClass,
            final SqlSetter setParam,
            final SqlGetter getParam) {

        this.typeName = typeName;
        this.primitiveClass = primitiveClass;
        this.nonPrimitiveClass = nonPrimitiveClass;
        this.setParam = setParam;
        this.getParam = getParam;
    }

    public String getTypeName() {
        return typeName;
    }

    public Class<?> getPrimitiveClass() {
        return primitiveClass;
    }

    public Class<?> getNonPrimitiveClass() {
        return nonPrimitiveClass;
    }

    public void setParameter(PreparedStatement statement, int position, Object value)
            throws DataAccessWriterException {

        try {
            this.setParam.accept(statement, position, value);
        }
        catch (SQLException e) {
            throw new DataAccessWriterException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getParameter(final ResultSet resultSet, final String position)
            throws DataAccessReaderException {

        try {
            return (T) this.getParam.accept(resultSet, position);
        }
        catch (SQLException e) {
            throw new DataAccessReaderException(e);
        }
    }

    /* GETTERS */
    private static boolean getBoolean(final ResultSet resultSet, final String pos)
            throws SQLException {
        return resultSet.getBoolean(pos);
    }

    private static byte getByte(final ResultSet resultSet, final String pos)
            throws SQLException {
        return resultSet.getByte(pos);
    }

    private static short getShort(final ResultSet resultSet, final String pos)
            throws SQLException {
        return resultSet.getShort(pos);
    }

    private static long getLong(final ResultSet resultSet, final String pos)
            throws SQLException {
        return resultSet.getLong(pos);
    }

    private static BigDecimal getBigDecimal(final ResultSet resultSet, final String pos)
            throws SQLException {
        return resultSet.getBigDecimal(pos);
    }

    private static int getInt(final ResultSet resultSet, final String pos)
            throws SQLException {
        return resultSet.getInt(pos);
    }

    private static double getDouble(final ResultSet resultSet, final String pos)
            throws SQLException {
        return resultSet.getDouble(pos);
    }

    private static float getFloat(final ResultSet resultSet, final String pos)
            throws SQLException {
        return resultSet.getFloat(pos);
    }

    private static java.sql.Time getTime(final ResultSet resultSet, final String pos)
            throws SQLException {
        return resultSet.getTime(pos);
    }

    private static java.sql.Date getDate(final ResultSet resultSet, final String pos)
            throws SQLException {
        return resultSet.getDate(pos);
    }

    private static java.util.Date getTimestamp(final ResultSet resultSet, final String pos)
            throws SQLException {
        return new java.util.Date(resultSet.getTimestamp(pos).getTime());
    }

    private static Object getObject(final ResultSet resultSet, final String pos)
            throws SQLException {
        return resultSet.getObject(pos);
    }

    private static java.util.UUID getUUID(final ResultSet resultSet, final String pos)
            throws SQLException {
        return (java.util.UUID) resultSet.getObject(pos);
    }

    private static String getString(final ResultSet resultSet, final String pos)
            throws SQLException {
        return resultSet.getString(pos);
    }

    private static byte[] getBytes(final ResultSet resultSet, final String pos)
            throws SQLException {
        return resultSet.getBytes(pos);
    }

    /* SETTERS */
    private static void setBoolean(final PreparedStatement preparedStatement, final Integer pos, final Object val)
            throws SQLException {
        preparedStatement.setBoolean(pos, (boolean) val);
    }

    private static void setByte(final PreparedStatement preparedStatement, final Integer pos, final Object val)
            throws SQLException {
        preparedStatement.setByte(pos, (byte) val);
    }

    private static void setShort(final PreparedStatement preparedStatement, final Integer pos, final Object val)
            throws SQLException {
        preparedStatement.setShort(pos, (short) val);
    }

    private static void setLong(final PreparedStatement preparedStatement, final Integer pos, final Object val)
            throws SQLException {
        preparedStatement.setLong(pos, (long) val);
    }

    private static void setBigDecimal(final PreparedStatement preparedStatement, final Integer pos, final Object val)
            throws SQLException {
        preparedStatement.setBigDecimal(pos, (BigDecimal) val);
    }

    private static void setInt(final PreparedStatement preparedStatement, final Integer pos, final Object val)
            throws SQLException {
        preparedStatement.setInt(pos, (int) val);
    }

    private static void setDouble(final PreparedStatement preparedStatement, final Integer pos, final Object val)
            throws SQLException {
        preparedStatement.setDouble(pos, (double) val);
    }

    private static void setFloat(final PreparedStatement preparedStatement, final Integer pos, final Object val)
            throws SQLException {
        preparedStatement.setFloat(pos, (float) val);
    }

    private static void setTime(final PreparedStatement preparedStatement, final Integer pos, final Object val)
            throws SQLException {
        preparedStatement.setTime(pos, (java.sql.Time) val);
    }

    private static void setDate(final PreparedStatement preparedStatement, final Integer pos, final Object val)
            throws SQLException {
        preparedStatement.setDate(pos, (java.sql.Date) val);
    }

    private static void setTimestamp(final PreparedStatement preparedStatement, final Integer pos, final Object val)
            throws SQLException {
        preparedStatement.setTimestamp(pos, new java.sql.Timestamp(((java.util.Date) val).getTime()));
    }

    private static void setObject(final PreparedStatement preparedStatement, final Integer pos, final Object val)
            throws SQLException {
        preparedStatement.setObject(pos, val);
    }

    private static void setUUID(final PreparedStatement preparedStatement, final Integer pos, final Object val)
            throws SQLException {
        preparedStatement.setObject(pos, val);
    }

    private static void setString(final PreparedStatement preparedStatement, final Integer pos, final Object val)
            throws SQLException {
        preparedStatement.setString(pos, (String) val);
    }

    private static void setBytes(final PreparedStatement preparedStatement, final Integer pos, final Object val)
            throws SQLException {
        preparedStatement.setBytes(pos, (byte[]) val);
    }
}
