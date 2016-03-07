package com.base2art.eventSourcedDataAccess.h2;

import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
import com.base2art.eventSourcedDataAccess.ObjectVersionFactory;
import com.base2art.eventSourcedDataAccess.h2.streamers.FilteredStreamer;
import com.base2art.eventSourcedDataAccess.h2.streamers.PagedFilteredStreamer;
import com.base2art.eventSourcedDataAccess.h2.streamers.PagedStreamer;
import com.base2art.eventSourcedDataAccess.h2.streamers.Streamer;
import com.base2art.eventSourcedDataAccess.impls.PojoDataAccessReaderBase;

import java.util.Optional;
import java.util.stream.Stream;

import static com.base2art.eventSourcedDataAccess.h2.H2Queries.fetchSingleObject;

public abstract class H2PojoDataAccessReader<Id, ObjectEntity, ObjectData, VersionObjectData, FilterOptions, OrderOptions>
        extends PojoDataAccessReaderBase<Id, ObjectEntity, ObjectData, VersionObjectData, FilterOptions, OrderOptions>
        implements DataProducer<Id, ObjectData, VersionObjectData> {

    private final H2Orderer<ObjectEntity, OrderOptions> orderer;
    private final H2Filterer<ObjectEntity, FilterOptions> filterer;
    private final H2Connector<Id> connector;

    public H2PojoDataAccessReader(
            final Class<ObjectEntity> objectEntityClass,
            final ObjectVersionFactory<Id, ObjectData, VersionObjectData, ObjectEntity> creationFunction,
            final H2Connector<Id> connector,
            final H2Filterer<ObjectEntity, FilterOptions> filterer,
            final H2Orderer<ObjectEntity, OrderOptions> orderer) {

        super(objectEntityClass, creationFunction);
        this.connector = connector;
        this.orderer = orderer;
        this.filterer = filterer;
    }

    @Override
    public Stream<ObjectEntity> stream() throws DataAccessReaderException {

        return new Streamer<>(this.connector, this, this).get();
    }

    @Override
    public Stream<ObjectEntity> streamFiltered(final FilterOptions filterOptions) throws DataAccessReaderException {

        return new FilteredStreamer<>(this.connector, this, this).get(filterOptions);
    }

    @Override
    public Stream<ObjectEntity> streamPaged(final OrderOptions orderOptions, Id marker, int pageSize) throws DataAccessReaderException {
//        Stream<ObjectEntity> stream = this.stream();
//        stream = orderEntities(stream, orderOptions);
//        return pageEntities(stream, marker, pageSize);

        return new PagedStreamer<>(this.connector, this, this).get(orderOptions, marker, pageSize);
    }

    @Override
    public Stream<ObjectEntity> streamFilteredAndPaged(
            final FilterOptions filterOptions,
            final OrderOptions orderOptions,
            final Id marker,
            final int pageSize)
            throws DataAccessReaderException {

        return new PagedFilteredStreamer<>(this.connector, this, this).get(filterOptions, orderOptions, marker, pageSize);
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
//
//    protected Stream<ObjectEntity> orderEntities(Stream<ObjectEntity> stream, OrderOptions options) {
//
//        if (options == null) {
//            return stream;
//        }
//
//        return ordererorder(stream, options);
//    }

//    protected Stream<ObjectEntity> pageEntities(Stream<ObjectEntity> stream, Id marker, int pageSize) {
//
//        // pageEntities();
//        if (marker == null) {
//            return stream.limit(pageSize);
//        }
//
//        return skipUntil(stream, x -> marker.equals(getIdForEntity(x)))
//                .skip(1)
//                .limit(pageSize);
//    }

    protected abstract Id getIdForEntity(final ObjectEntity x);
}