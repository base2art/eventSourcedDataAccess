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

    private final H2Connector<Id> connector;

    public H2PojoDataAccessReader(
            final Class<ObjectEntity> objectEntityClass,
            final ObjectVersionFactory<Id, ObjectData, VersionObjectData, ObjectEntity> creationFunction,
            final H2Connector<Id> connector) {

        super(objectEntityClass, creationFunction);
        this.connector = connector;
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

        return fetchSingleObject(
                this.connector,
                id,
                Sql.latestVersionByObjectId(this.connector),
                this.connector.nonFinalObjectVersionDataFields(),
                this::createVersionObjectData);
    }
}