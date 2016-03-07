package com.base2art.eventSourcedDataAccess.impls;

import com.base2art.eventSourcedDataAccess.DataAccessReader;
import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
import com.base2art.eventSourcedDataAccess.EntityProducer;
import com.base2art.eventSourcedDataAccess.FilteredDataAccessReader;
import com.base2art.eventSourcedDataAccess.FilteredPagedDataAccessReader;
import com.base2art.eventSourcedDataAccess.ItemDataAccessReader;
import com.base2art.eventSourcedDataAccess.ObjectVersionFactory;
import com.base2art.eventSourcedDataAccess.PagedDataAccessReader;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.base2art.eventSourcedDataAccess.utils.Arrays.createGenericArray;

public abstract class PojoDataAccessReaderBase<Id, ObjectEntity, ObjectData, VersionObjectData, FilterOptions, OrderOptions>
        implements DataAccessReader<ObjectEntity>,
                   FilteredDataAccessReader<ObjectEntity, FilterOptions>,
                   FilteredPagedDataAccessReader<Id, ObjectEntity, FilterOptions, OrderOptions>,
                   PagedDataAccessReader<Id, ObjectEntity, OrderOptions>,
                   ItemDataAccessReader<Id, ObjectEntity>,
                   EntityProducer<Id, ObjectEntity, ObjectData, VersionObjectData> {

    private final Class<ObjectEntity> objectEntityClass;
    private final ObjectVersionFactory<Id, ObjectData, VersionObjectData, ObjectEntity> creationFunction;

    public PojoDataAccessReaderBase(
            final Class<ObjectEntity> objectEntityClass,
            final ObjectVersionFactory<Id, ObjectData, VersionObjectData, ObjectEntity> creationFunction) {

        this.objectEntityClass = objectEntityClass;
        this.creationFunction = creationFunction;
    }

    @Override
    public ObjectEntity getItem(final Id id) throws DataAccessReaderException {

        Optional<ObjectData> objectData = getObjectDataById(id);

        if (!objectData.isPresent()) {
            return null;
        }

        Optional<VersionObjectData> versionObjectData = getVersionObjectDataById(id);

        return getObjectEntity(id, objectData, versionObjectData);
    }

    @Override
    public abstract Stream<ObjectEntity> stream()
            throws DataAccessReaderException;

    @Override
    public abstract Stream<ObjectEntity> streamFiltered(final FilterOptions filterOptions)
            throws DataAccessReaderException;

    @Override
    public abstract Stream<ObjectEntity> streamPaged(
            final OrderOptions orderOptions,
            final Id marker,
            final int pageSize)
            throws DataAccessReaderException;

    @Override
    public abstract Stream<ObjectEntity> streamFilteredAndPaged(
            final FilterOptions filterOptions,
            final OrderOptions orderOptions,
            final Id marker,
            final int pageSize)
            throws DataAccessReaderException;

    @Override
    public ObjectEntity[] get() throws DataAccessReaderException {

        return compileToArray(stream());
    }

    @Override
    public ObjectEntity[] getFiltered(final FilterOptions filterOptions) throws DataAccessReaderException {
        return compileToArray(streamFiltered(filterOptions));
    }

    @Override
    public ObjectEntity[] getPaged(final OrderOptions orderOptions, final Id marker, final int pageSize) throws DataAccessReaderException {
        return compileToArray(streamPaged(orderOptions, marker, pageSize));
    }

    @Override
    public ObjectEntity[] getFilteredAndPaged(
            final FilterOptions filterOptions,
            final OrderOptions orderOptions,
            final Id marker,
            final int pageSize)
            throws DataAccessReaderException {

        return compileToArray(streamFilteredAndPaged(filterOptions, orderOptions, marker, pageSize));
    }

    @Override
    public ObjectEntity getObjectEntity(
            final Id id,
            final Optional<ObjectData> objectData,
            final Optional<VersionObjectData> versionObjectData) {

        if (!versionObjectData.isPresent()) {
            return null;
        }

        return this.creationFunction.apply(id, objectData.get(), versionObjectData.get());
    }

    protected abstract Optional<ObjectData> getObjectDataById(Id id)
            throws DataAccessReaderException;

    protected abstract Optional<VersionObjectData> getVersionObjectDataById(final Id id)
            throws DataAccessReaderException;

    private ObjectEntity[] compileToArray(final Stream<ObjectEntity> objectEntityStream) {

        List<ObjectEntity> entities = objectEntityStream.collect(Collectors.toList());

        ObjectEntity[] arr = createGenericArray(this.objectEntityClass, entities.size());

        for (int i = 0; i < arr.length; i++) {
            arr[i] = entities.get(i);
        }

        return arr;
    }
}