package com.base2art.eventSourcedDataAccess.h2;

import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
import com.base2art.eventSourcedDataAccess.DataAccessWriterException;
import com.base2art.eventSourcedDataAccess.impls.PojoDataAccessWriterBase;
import lombok.Data;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class H2PojoDataAccessWriter<Id, ObjectData, VersionObjectData>
        extends PojoDataAccessWriterBase<Id, ObjectData, VersionObjectData> {

    private final H2Connector<Id> connector;

    public H2PojoDataAccessWriter(
            H2Connector connector) {

        this.connector = connector;
    }

    @Override
    protected boolean hasObject(final Id id)
            throws DataAccessWriterException {

        try {
            try (Connection connection = this.connector.openConnection()) {

                String sql = "SELECT COUNT(*) FROM " + this.connector.objectTable() + " WHERE object_id = ?";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setObject(1, id);

                    try (ResultSet set = statement.executeQuery()) {
                        if (set.next()) {
                            return set.getInt(1) > 0;
                        }
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessWriterException(e);
        }

        return false;
    }

    @Override
    protected void insertVersionInternal(final Id id, final VersionObjectData version)
            throws DataAccessWriterException {

        this.insertRecord(
                this.connector.objectVersionTable(),
                id,
                version,
                this.connector.nonFinalObjectVersionDataFields(),
                "object_id",
                "?");
    }

    @Override
    protected void createObjectInternal(final Id id, final ObjectData object)
            throws DataAccessWriterException {

        this.insertRecord(
                this.connector.objectTable(),
                id,
                object,
                this.connector.nonFinalObjectDataFields(),
                "object_id",
                "?");
    }

    @Override
    protected void archiveObjectInternal(final Id id) throws DataAccessWriterException {

        this.insertRecord(
                this.connector.objectTable(),
                id,
                new ArchivableObject(),
                this.connector.nonFinalObjectDataFields(),
                "object_id",
                "?");
    }

    private void insertRecord(
            final String tableName,
            final Id id,
            final Object object,
            final List<Field> fields,
            final String baseFieldNames,
            final String baseParamNames)
            throws DataAccessWriterException {

        try (Connection connection = this.connector.openConnection()) {

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

                this.connector.idH2Type()
                              .setParameter(statement, 1, id);

                for (int i = 0; i < fields.size(); i++) {
                    Field field = fields.get(i);
                    H2Type types = this.connector.getTypeByField(field);
                    field.setAccessible(true);
                    types.setParameter(statement, i + 2, field.get(object));
                }

                statement.executeUpdate();
            }

            connection.commit();
        }
        catch (IllegalAccessException | SQLException | DataAccessReaderException e) {
            throw new DataAccessWriterException(e);
        }
    }

    @Data
    private class ArchivableObject {
        private boolean isArchived = true;
    }
}