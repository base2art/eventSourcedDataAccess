package com.base2art.eventSourcedDataAccess.git;

import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
import com.base2art.eventSourcedDataAccess.DataAccessWriterException;
import com.base2art.eventSourcedDataAccess.FilteredPagedDataAccessReader;
import com.base2art.eventSourcedDataAccess.ItemDataAccessReader;
import com.base2art.eventSourcedDataAccess.ObjectVersionFactory;
import com.base2art.eventSourcedDataAccess.StreamFilterer;
import com.base2art.eventSourcedDataAccess.StreamOrderer;
import com.base2art.eventSourcedDataAccess.impls.PojoDataAccessReaderBase;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import static com.codepoetics.protonpack.StreamUtils.skipUntil;

public abstract class GitDataAccessReader<Id, ObjectEntity, ObjectData, VersionObjectData, FilterOptions, OrderOptions>
        extends PojoDataAccessReaderBase<Id, ObjectEntity, ObjectData, VersionObjectData, FilterOptions, OrderOptions>
        implements FilteredPagedDataAccessReader<Id, ObjectEntity, FilterOptions, OrderOptions>,
                   ItemDataAccessReader<Id, ObjectEntity> {

    private final Class<VersionObjectData> objectVersionDataType;
    private final Class<ObjectData> objectDataType;
    private final GitReader<Id> reader;
    private final StreamOrderer<ObjectEntity, OrderOptions> orderer;
    private final StreamFilterer<ObjectEntity, FilterOptions> filterer;

    public GitDataAccessReader(
            final Class<ObjectEntity> catalogType,
            final Class<ObjectData> objectDataType,
            final Class<VersionObjectData> objectVersionDataType,
            final ObjectVersionFactory<Id, ObjectData, VersionObjectData, ObjectEntity> factory,
            final GitReader<Id> reader,
            final StreamFilterer<ObjectEntity, FilterOptions> filterer,
            final StreamOrderer<ObjectEntity, OrderOptions> orderer) {

        super(catalogType, factory);
        this.objectDataType = objectDataType;
        this.objectVersionDataType = objectVersionDataType;
        this.reader = reader;
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

        return skipUntil(stream, x -> marker.equals(getIdForEntity(x))).skip(1)
                                                                       .limit(pageSize);
    }


    public Stream<ObjectEntity> createStream()
            throws DataAccessReaderException {


        try {
            return Arrays.stream(this.reader.getCatalog()
                                            .listFiles((x, y) -> !y.startsWith(".")))
                         .map(x -> {
                             try {
                                 return getItem(reader.parseId(x.getName()));
                             }
                             catch (DataAccessReaderException e) {
                                 throw new RuntimeException(e);
                             }
                         });
        }
        catch (DataAccessWriterException e) {
            throw new DataAccessReaderException(e);
        }
    }


    @Override
    protected Optional<ObjectData> getObjectDataById(final Id id) throws DataAccessReaderException {
        return this.reader.hasObject(id)
               ? Optional.of(this.reader.getObjectData(id, this.objectDataType))
               : Optional.<ObjectData>empty();
    }

    @Override
    protected Optional<VersionObjectData> getVersionObjectDataById(final Id id)
            throws DataAccessReaderException {
        return this.reader.hasObject(id)
               ? Optional.ofNullable(this.reader.getLatestVersionObjectDataById(id, this.objectVersionDataType))
               : Optional.<VersionObjectData>empty();
    }


    protected abstract Id getIdForEntity(final ObjectEntity x);
}
