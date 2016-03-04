package com.base2art.eventSourcedDataAccess.h2;

import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
import com.base2art.eventSourcedDataAccess.DataAccessWriterException;
import com.base2art.eventSourcedDataAccess.ObjectVersionFactory;
import com.base2art.eventSourcedDataAccess.extensions.Filterer;
import com.base2art.eventSourcedDataAccess.extensions.Orderer;
import com.base2art.eventSourcedDataAccess.impls.PojoDataAccessReaderBase;
import lombok.val;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Stream;

import static com.codepoetics.protonpack.StreamUtils.skipUntil;

public abstract class H2PojoDataAccessReader<Id, ObjectEntity, ObjectData, VersionObjectData, FilterOptions, OrderOptions>
        extends PojoDataAccessReaderBase<Id, ObjectEntity, ObjectData, VersionObjectData, FilterOptions, OrderOptions> {


    private final Orderer<ObjectEntity, OrderOptions> orderer;
    private final Filterer<ObjectEntity, FilterOptions> filterer;
    private final H2Connector<Id> connector;

    public H2PojoDataAccessReader(
            final Class<ObjectEntity> objectEntityClass,
            final ObjectVersionFactory<Id, ObjectData, VersionObjectData, ObjectEntity> creationFunction,
            final H2Connector<Id> connector,
            final Filterer<ObjectEntity, FilterOptions> filterer,
            final Orderer<ObjectEntity, OrderOptions> orderer) {

        super(objectEntityClass, creationFunction);
        this.connector = connector;
        this.orderer = orderer;
        this.filterer = filterer;
    }

    @Override
    public Stream<ObjectEntity> stream() throws DataAccessReaderException {
        return this.createStream();
    }

    @Override
    public Stream<ObjectEntity> streamFiltered(final FilterOptions filterOptions) throws DataAccessReaderException {
        return filterEntities(this.createStream(), filterOptions);
    }

    @Override
    public Stream<ObjectEntity> streamPaged(final OrderOptions orderOptions, Id marker, int pageSize) throws DataAccessReaderException {
        Stream<ObjectEntity> stream = this.createStream();
        stream = orderEntities(stream, orderOptions);
        return pageEntities(stream, marker, pageSize);
    }

    @Override
    public Stream<ObjectEntity> streamFilteredAndPaged(
            final FilterOptions filterOptions,
            final OrderOptions orderOptions,
            final Id marker,
            final int pageSize)
            throws DataAccessReaderException {

        Stream<ObjectEntity> entities = this.streamFiltered(filterOptions);
        entities = orderEntities(entities, orderOptions);

        return pageEntities(entities, marker, pageSize);
    }

    @Override
    protected Optional<ObjectData> getObjectDataById(final Id id)
            throws DataAccessReaderException {

        H2Type type = this.connector.idH2Type();
        try (Connection connection = this.connector.openConnection()) {

            String sql = "SELECT * FROM " + this.connector.objectTable() + " WHERE object_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                type.setParameter(statement, 1, id);

                try (ResultSet set = statement.executeQuery()) {

                    while (set.next()) {
                        val objectData = this.createObjectData(id);
                        this.populateData(this.connector.nonFinalObjectDataFields(), objectData, set);
                        return Optional.of(objectData);
                    }
                }
            }
            catch (DataAccessWriterException e) {
                throw new DataAccessReaderException(e);
            }
        }
        catch (SQLException e) {
            throw new DataAccessReaderException(e);
        }

        return Optional.empty();
    }

    @Override
    protected Optional<VersionObjectData> getVersionObjectDataById(final Id id) throws DataAccessReaderException {


        H2Type type = this.connector.idH2Type();
        try (Connection connection = this.connector.openConnection()) {


            String sqlVersion = "SELECT p1.*\n" +
                                "FROM " + this.connector.objectVersionTable() + " p1" +
                                "  LEFT JOIN " + this.connector.objectVersionTable() + " p2" +
                                "     ON (p1.object_id = p2.object_id) AND (p1.OBJECT_VERSION_ID < p2.OBJECT_VERSION_ID)\n" +
                                "WHERE p2.object_version_id IS NULL AND p1.object_id in (?);";
            try (PreparedStatement statement = connection.prepareStatement(sqlVersion)) {

                type.setParameter(statement, 1, id);

                try (ResultSet set = statement.executeQuery()) {
                    while (set.next()) {
                        VersionObjectData objectData = this.createVersionObjectData(id);
                        this.populateData(this.connector.nonFinalObjectVersionDataFields(), objectData, set);
                        return Optional.of(objectData);
                    }
                }
            }
            catch (DataAccessWriterException e) {
                throw new DataAccessReaderException(e);
            }
        }
        catch (SQLException e) {
            throw new DataAccessReaderException(e);
        }

        return Optional.empty();
    }

    protected Stream<ObjectEntity> filterEntities(Stream<ObjectEntity> stream, FilterOptions options) {

        if (options == null) {
            return stream;
        }

        return filterer.filter(stream, options);
    }

    protected Stream<ObjectEntity> orderEntities(Stream<ObjectEntity> stream, OrderOptions options) {

        if (options == null) {
            return stream;
        }

        return orderer.order(stream, options);
    }

    protected Stream<ObjectEntity> pageEntities(Stream<ObjectEntity> stream, Id marker, int pageSize) {

        // pageEntities();
        if (marker == null) {
            return stream.limit(pageSize);
        }

        return skipUntil(stream, x -> marker.equals(getIdForEntity(x)))
                .skip(1)
                .limit(pageSize);
    }

    protected abstract Id getIdForEntity(final ObjectEntity x);


    private Stream<ObjectEntity> createStream() throws DataAccessReaderException {

        Map<Id, ObjectData> objectDatas = new HashMap<>();
        Map<Id, VersionObjectData> versionObjectDatas = new HashMap<>();

        try (Connection connection = this.connector.openConnection()) {


            H2Type type = this.connector.idH2Type();
            String sql = "SELECT * FROM " + this.connector.objectTable();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet set = statement.executeQuery()) {

                    while (set.next()) {
                        Id id = type.getParameter(set, "object_id");
                        ObjectData objectData = this.createObjectData(id);
                        this.populateData(connector.nonFinalObjectDataFields(), objectData, set);
                        objectDatas.put(id, objectData);
                    }
                }
            }


            Id[] ids = objectDatas.keySet()
                                  .toArray(createGenericArray(this.connector.idType(), 0));
            StringJoiner joiner = new StringJoiner(", ", "(", ")");
            for (int i = 0; i < ids.length; i++) {
                joiner.add("?");
            }

            String sqlVersion = "SELECT p1.*\n" +
                                "FROM " + this.connector.objectVersionTable() + " p1" +
                                "  LEFT JOIN " + this.connector.objectVersionTable() + " p2" +
                                "     ON (p1.object_id = p2.object_id) AND (p1.OBJECT_VERSION_ID < p2.OBJECT_VERSION_ID)\n" +
                                "WHERE p2.object_version_id IS NULL AND p1.object_id in " +
                                joiner.toString();
            try (PreparedStatement statement = connection.prepareStatement(sqlVersion)) {

                for (int i = 0; i < ids.length; i++) {
                    type.setParameter(statement, 1 + i, ids[i]);
                }

                try (ResultSet set = statement.executeQuery()) {
                    while (set.next()) {
                        Id id = type.getParameter(set, "object_id");
                        VersionObjectData objectData = this.createVersionObjectData(id);
                        this.populateData(this.connector.nonFinalObjectVersionDataFields(), objectData, set);
                        versionObjectDatas.put(id, objectData);
                    }
                }
            }
        }
        catch (DataAccessWriterException | SQLException e) {
            throw new DataAccessReaderException(e);
        }

        List<ObjectEntity> items = new ArrayList<>();
        for (Map.Entry<Id, ObjectData> pair : objectDatas.entrySet()) {
            items.add(this.getObjectEntity(
                    pair.getKey(),
                    Optional.ofNullable(pair.getValue()),
                    Optional.ofNullable(versionObjectDatas.getOrDefault(pair.getKey(), null))));
        }

        return items.stream();
    }

    protected abstract VersionObjectData createVersionObjectData(final Id id);

    protected abstract ObjectData createObjectData(final Id id);

    protected <T> void populateData(
            final List<Field> fields,
            final T objectData,
            final ResultSet set) throws DataAccessReaderException {

        for (Field field : fields) {
            H2Type type = this.connector.getTypeByField(field);
            field.setAccessible(true);
            try {
                field.set(objectData, type.getParameter(set, field.getName()));
            }
            catch (IllegalAccessException | DataAccessReaderException e) {
                throw new DataAccessReaderException(e);
            }
        }
    }
}