package com.base2art.eventSourcedDataAccess.impls;

import com.base2art.eventSourcedDataAccess.DataAccessWriter;
import com.base2art.eventSourcedDataAccess.DataAccessWriterException;

public abstract class PojoDataAccessWriterBase<Id, ObjectData, VersionObjectData>
        implements DataAccessWriter<Id, ObjectData, VersionObjectData> {

    @Override
    public final void createObject(final Id id, final ObjectData object) throws DataAccessWriterException {
        if (this.hasObject(id)) {
            throw new DataAccessWriterException("already has object: " + id);
        }

        this.createObjectInternal(id, object);
    }

    @Override
    public final void insertVersion(final Id id, final VersionObjectData version) throws DataAccessWriterException {

        if (!hasObject(id)) {
            throw new DataAccessWriterException("object doesn't exist: " + id);
        }

        this.insertVersionInternal(id, version);
    }

    @Override
    public final void archiveObject(final Id id) throws DataAccessWriterException {

        if (!hasObject(id)) {
            throw new DataAccessWriterException("object doesn't exist: " + id);
        }

        this.archiveObjectInternal(id);
    }


    protected abstract boolean hasObject(final Id id)
            throws DataAccessWriterException;

    protected abstract void insertVersionInternal(final Id id, final VersionObjectData version)
            throws DataAccessWriterException;

    protected abstract void createObjectInternal(final Id id, final ObjectData object)
            throws DataAccessWriterException;

    protected abstract void archiveObjectInternal(final Id id)
            throws DataAccessWriterException;
}
