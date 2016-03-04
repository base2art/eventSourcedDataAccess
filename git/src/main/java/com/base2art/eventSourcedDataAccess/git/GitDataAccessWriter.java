package com.base2art.eventSourcedDataAccess.git;

import com.base2art.eventSourcedDataAccess.DataAccessWriterException;
import com.base2art.eventSourcedDataAccess.impls.PojoDataAccessWriterBase;

public class GitDataAccessWriter<Id, ObjectData, VersionObjectData>
        extends PojoDataAccessWriterBase<Id, ObjectData, VersionObjectData> {

    private final GitWriter<Id> holder;

    public GitDataAccessWriter(final GitWriter<Id> holder) {
        this.holder = holder;
    }

    public void ensureUpdated() {
        this.holder.ensureUpdated();
    }

    public void ensureUpdatedAsync() {
        this.holder.ensureUpdatedAsync();
    }

    @Override
    protected boolean hasObject(final Id id) {
        return this.holder.hasObject(id);
    }

    @Override
    protected void insertVersionInternal(final Id id, final VersionObjectData version) throws DataAccessWriterException {

        holder.<VersionObjectData>writeObject(id, versionId(version), version);
    }

    @Override
    protected void createObjectInternal(final Id id, final ObjectData object) throws DataAccessWriterException {

        holder.<ObjectData>writeObject(id, ".meta", object);
    }

    @Override
    protected void archiveObjectInternal(final Id id) throws DataAccessWriterException {

        holder.<Object>writeObject(id, ".archived", new Object());
    }

    protected String versionId(final VersionObjectData version) {
        return String.valueOf(System.currentTimeMillis());
    }
}
