package com.base2art.eventSourcedDataAccess.memory;

import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
import com.base2art.eventSourcedDataAccess.ObjectVersionFactory;
import com.base2art.eventSourcedDataAccess.StreamFilterer;
import com.base2art.eventSourcedDataAccess.StreamOrderer;
import com.base2art.eventSourcedDataAccess.impls.PojoDataAccessReaderBase;
import com.google.common.collect.Multimap;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static com.codepoetics.protonpack.StreamUtils.skipUntil;

public abstract class InMemoryPojoDataAccessReader<Id, ObjectEntity, ObjectData, VersionObjectData, FilterOptions, OrderOptions>
        extends PojoDataAccessReaderBase<Id, ObjectEntity, ObjectData, VersionObjectData, FilterOptions, OrderOptions> {

    private final Map<Id, Archivable<ObjectData>> objectData;
    private final Multimap<Id, TimeStamped<VersionObjectData>> versionObjectData;
    private final StreamOrderer<ObjectEntity, OrderOptions> orderer;
    private final StreamFilterer<ObjectEntity, FilterOptions> filterer;

    public InMemoryPojoDataAccessReader(
            final Class<ObjectEntity> objectEntityClass,
            final ObjectVersionFactory<Id, ObjectData, VersionObjectData, ObjectEntity> creationFunction,
            final Map<Id, Archivable<ObjectData>> objectData,
            final Multimap<Id, TimeStamped<VersionObjectData>> versionObjectData,
            final StreamFilterer<ObjectEntity, FilterOptions> filterer,
            final StreamOrderer<ObjectEntity, OrderOptions> orderer) {

        super(objectEntityClass, creationFunction);

        this.objectData = objectData;
        this.versionObjectData = versionObjectData;
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
    protected Optional<ObjectData> getObjectDataById(final Id id) {
        return this.objectData.containsKey(id)
               ? Optional.of(this.objectData.get(id).getData())
               : Optional.<ObjectData>empty();
    }

    @Override
    protected Optional<VersionObjectData> getVersionObjectDataById(final Id id) {

        if (this.versionObjectData.containsKey(id)) {

            return this.versionObjectData.get(id)
                                         .stream()
                                         .sorted((x, y) -> 0 - x.getTime().compareTo(y.getTime()))
                                         .map(TimeStamped::getData)
                                         .findFirst();
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

        return this.objectData.entrySet()
                              .stream()
                              .filter(x -> !x.getValue().isArchived())
                              .map(x -> {
                                  try {
                                      return getItem(x.getKey());
                                  }
                                  catch (DataAccessReaderException e) {
                                      throw new RuntimeException(e);
                                  }
                              });
    }
}