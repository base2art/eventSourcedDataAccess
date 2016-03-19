package com.base2art.eventSourcedDataAccess.h2;

import com.base2art.eventSourcedDataAccess.DataAccessWriterException;
import lombok.val;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public final class H2Writes {

    public static <Id> void insertRecord(
            final H2Connector<Id> connector,
            final String tableName,
            final Id id,
            final Object object,
            final List<Field> fields,
            final String baseFieldNames,
            final String baseParamNames)
            throws DataAccessWriterException {

        try (Connection connection = connector.openConnection()) {

            StringBuilder fieldNames = new StringBuilder(baseFieldNames);
            StringBuilder params = new StringBuilder(baseParamNames);
            for (int i = 0; i < fields.size(); i++) {
                fieldNames.append(", ")
                          .append(fields.get(i).getName());

                params.append(", ")
                      .append("?");
            }

            String sql = new StringBuilder().append("INSERT INTO ")
                                            .append(tableName)
                                            .append("(")
                                            .append(fieldNames)
                                            .append(")")
                                            .append(" VALUES ")
                                            .append("(")
                                            .append(params)
                                            .append(")")
                                            .toString();

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                connector.idH2Type()
                         .setParameter(statement, 1, id);

                for (int i = 0; i < fields.size(); i++) {
                    Field field = fields.get(i);
                    val types = connector.getTypeByField(field);
                    field.setAccessible(true);
                    types.setParameter(statement, i + 2, field.get(object));
                }

                statement.executeUpdate();
            }

            connection.commit();
        }
        catch (IllegalAccessException | SQLException e) {
            throw new DataAccessWriterException(e);
        }
    }
}
