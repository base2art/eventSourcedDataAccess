package com.base2art.eventSourcedDataAccess.h2;

import com.base2art.eventSourcedDataAccess.h2.parameters.RawH2Type;
import org.h2.jdbcx.JdbcConnectionPool;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

import static com.base2art.eventSourcedDataAccess.utils.Reflection.fields;

public class H2Connector<Id> {

    private JdbcConnectionPool connectionPool;

    private final String location;
    private final String catalogName;
    private final Class<Id> idType;
    private final Class<?> objectDataClass;
    private final Class<?> objectVersionDataClass;
    private boolean hasLoaded;

    public H2Connector(
            final String location,
            final String catalogName,
            final Class<Id> idType,
            final Class<?> objectDataClass,
            final Class<?> objectVersionDataClass) {
        this.location = location;
        this.catalogName = catalogName;
        this.idType = idType;
        this.objectDataClass = objectDataClass;
        this.objectVersionDataClass = objectVersionDataClass;
    }

    public Connection openConnection() throws SQLException {
        this.ensureLoaded();
        return this.connectionPool.getConnection();
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public String objectTable() {
        return this.getCatalogName() + "_object";
    }

    public String objectStatusTable() {
        return this.getCatalogName() + "_object_status";
    }

    public String objectVersionTable() {
        return this.getCatalogName() + "_object_version";
    }

    public List<Field> nonFinalObjectDataFields() {

        return fields(this.objectDataClass).filter(x -> !Modifier.isFinal(x.getModifiers()))
                                           .collect(Collectors.toList());
    }

    public List<Field> nonFinalObjectVersionDataFields() {

        return fields(this.objectVersionDataClass).filter(x -> !Modifier.isFinal(x.getModifiers()))
                                                  .collect(Collectors.toList());
    }

    public RawH2Type idH2Type() {
        return H2TypeRegistrar.getTypeByField(this.idType);
    }

    public RawH2Type getTypeByField(final Field field) {
        return H2TypeRegistrar.getTypeByField(field.getType());
    }

    public Class<Id> idType() {
        return this.idType;
    }

    private void ensureLoaded() throws SQLException {

        if (hasLoaded) {
            return;
        }

        org.h2.Driver.load();
        if (this.connectionPool == null) {
            this.connectionPool = JdbcConnectionPool.create(
                    "jdbc:h2:" + this.location,
                    "sa",
                    "sa");
        }

        try (Connection connection = this.connectionPool.getConnection()) {

            try (Statement objectStatement = connection.createStatement()) {
                StringBuilder objectSql = new StringBuilder();

                objectSql.append("CREATE TABLE IF NOT EXISTS ").append(this.objectTable()).append(" (").append("\n");
                objectSql.append("  object_id " + this.idH2Type().getTypeName() + " NOT NULL").append("\n");
                objectSql.append(")").append("\n");
                objectStatement.executeUpdate(objectSql.toString());
            }

            try (Statement objectStatement = connection.createStatement()) {
                StringBuilder objectSql = new StringBuilder();

                objectSql.append("CREATE TABLE IF NOT EXISTS ").append(this.objectStatusTable()).append(" (").append("\n");
                objectSql.append("  object_status_version_id IDENTITY NOT NULL,").append("\n");
                objectSql.append("  object_id " + this.idH2Type().getTypeName() + " NOT NULL,").append("\n");
                objectSql.append("  isArchived BOOLEAN NOT NULL").append("\n");
                objectSql.append(")").append("\n");
                objectStatement.executeUpdate(objectSql.toString());
            }

            updateTableColumns(connection, this.objectTable(), nonFinalObjectDataFields());

            try (Statement objectVersionStatement = connection.createStatement()) {
                StringBuilder sql = new StringBuilder();

                sql.append("CREATE TABLE IF NOT EXISTS ").append(this.objectVersionTable()).append(" (").append("\n");
                sql.append("  object_version_id IDENTITY NOT NULL,").append("\n");
                sql.append("  object_id " + this.idH2Type().getTypeName() + " NOT NULL").append("\n");
                sql.append(")").append("\n");

                objectVersionStatement.executeUpdate(sql.toString());
            }

            updateTableColumns(connection, this.objectVersionTable(), nonFinalObjectVersionDataFields());

            connection.commit();
        }

        hasLoaded = true;
    }

    private void updateTableColumns(
            final Connection connection,
            final String clazzName,
            final List<Field> fields) throws SQLException {

        for (Field field : fields) {

            try (Statement objectColumnStatement = connection.createStatement()) {
                StringBuilder objectColumnSql = new StringBuilder();

                objectColumnSql.append("ALTER TABLE ").append(clazzName).append(" ");
                objectColumnSql.append("ADD COLUMN IF NOT EXISTS ").append(field.getName()).append(" ");
                objectColumnSql.append(getTypeByField(field).getTypeName());
                objectColumnStatement.executeUpdate(objectColumnSql.toString());
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

            /*

DROP TABLE IF EXISTS test1;

CREATE TABLE IF NOT EXISTS test1 (
    col16 VARCHAR(16) NOT NULL
);

ALTER TABLE test1
ADD col2 INT;

INSERT INTO test1 (col16, col2) VALUES ('sdf', 1);
INSERT INTO test1 (col16, col2) VALUES ('wer', 2);
INSERT INTO test1 (col16, col2) VALUES ('rty', 3);
INSERT INTO test1 (col16, col2) VALUES ('456', 4);

SELECT col2 FROM test1;

            */