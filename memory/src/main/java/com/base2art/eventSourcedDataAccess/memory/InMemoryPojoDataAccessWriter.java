package com.base2art.eventSourcedDataAccess.memory;

import com.base2art.eventSourcedDataAccess.impls.PojoDataAccessWriterBase;
import com.google.common.collect.Multimap;

import java.util.Map;

public class InMemoryPojoDataAccessWriter<Id, ObjectData, VersionObjectData>
        extends PojoDataAccessWriterBase<Id, ObjectData, VersionObjectData> {


    private final Map<Id, Archivable<ObjectData>> objectData;
    private final Multimap<Id, TimeStamped<VersionObjectData>> versionObjectData;

    public InMemoryPojoDataAccessWriter(
            final Map<Id, Archivable<ObjectData>> objectData,
            final Multimap<Id, TimeStamped<VersionObjectData>> versionObjectData) {

        this.objectData = objectData;
        this.versionObjectData = versionObjectData;
    }

    @Override
    protected boolean hasObject(final Id id) {
        return this.objectData.containsKey(id);
    }

    @Override
    protected void insertVersionInternal(final Id id, final VersionObjectData version) {
        this.versionObjectData.put(id, new TimeStamped<>(version));
    }

    @Override
    protected void createObjectInternal(final Id id, final ObjectData object) {
        this.objectData.put(id, new Archivable<>(object));
    }

    @Override
    protected void archiveObjectInternal(final Id id) {
        this.objectData.get(id).setIsArchived(true);
    }
}