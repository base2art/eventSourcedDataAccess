package com.base2art.eventSourcedDataAccess.h2;

import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
import com.base2art.eventSourcedDataAccess.ObjectVersionFactory;
import com.base2art.eventSourcedDataAccess.extensions.Filterer;
import com.base2art.eventSourcedDataAccess.extensions.Orderer;
import com.base2art.eventSourcedDataAccess.impls.PojoDataAccessReaderBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Stream;

import static com.base2art.eventSourcedDataAccess.h2.H2Queries.fetchObjectMap;
import static com.base2art.eventSourcedDataAccess.h2.H2Queries.fetchSingleObject;
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

        String sql = "SELECT * FROM " + this.connector.objectTable();

        Map<Id, ObjectData> objectDatas = fetchObjectMap(
                connector,
                sql,
                null,
                this.connector.nonFinalObjectDataFields(),
                this::createObjectData);

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

        H2Type type = this.connector.idH2Type();
        Map<Id, VersionObjectData> versionObjectDatas = fetchObjectMap(
                connector,
                sqlVersion,
                (stmt) -> {
                    for (int i = 0; i < ids.length; i++) {
                        type.setParameter(stmt, 1 + i, ids[i]);
                    }
                },
                this.connector.nonFinalObjectVersionDataFields(),
                this::createVersionObjectData);

        List<ObjectEntity> items = new ArrayList<>();
        for (Map.Entry<Id, ObjectData> pair : objectDatas.entrySet()) {
            items.add(this.getObjectEntity(
                    pair.getKey(),
                    Optional.ofNullable(pair.getValue()),
                    Optional.ofNullable(versionObjectDatas.getOrDefault(pair.getKey(), null))));
        }

        return items.stream();
    }

    @Override
    public Stream<ObjectEntity> streamFiltered(final FilterOptions filterOptions) throws DataAccessReaderException {
        return filterEntities(this.stream(), filterOptions);
    }

    @Override
    public Stream<ObjectEntity> streamPaged(final OrderOptions orderOptions, Id marker, int pageSize) throws DataAccessReaderException {
        Stream<ObjectEntity> stream = this.stream();
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

        return fetchSingleObject(
                this.connector,
                id,
                "SELECT * FROM " + this.connector.objectTable() + " WHERE object_id = ?",
                this.connector.nonFinalObjectDataFields(),
                this::createObjectData);
    }

    @Override
    protected Optional<VersionObjectData> getVersionObjectDataById(final Id id) throws DataAccessReaderException {

        String sqlVersion = "SELECT p1.*" +
                            "  FROM " + this.connector.objectVersionTable() + " p1" +
                            "    LEFT JOIN " + this.connector.objectVersionTable() + " p2" +
                            "      ON (p1.object_id = p2.object_id) AND (p1.OBJECT_VERSION_ID < p2.OBJECT_VERSION_ID)\n" +
                            "  WHERE p2.object_version_id IS NULL AND p1.object_id in (?);";


        return fetchSingleObject(
                this.connector,
                id,
                sqlVersion,
                this.connector.nonFinalObjectVersionDataFields(),
                this::createVersionObjectData);
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

    protected abstract VersionObjectData createVersionObjectData(final Id id);

    protected abstract ObjectData createObjectData(final Id id);
}