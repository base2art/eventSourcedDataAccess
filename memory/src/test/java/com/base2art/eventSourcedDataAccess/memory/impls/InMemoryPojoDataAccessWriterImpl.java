package com.base2art.eventSourcedDataAccess.memory.impls;

import com.base2art.eventSourcedDataAccess.memory.Archivable;
import com.base2art.eventSourcedDataAccess.memory.InMemoryPojoDataAccessWriter;
import com.base2art.eventSourcedDataAccess.memory.TimeStamped;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonData;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonVersionData;
import com.google.common.collect.Multimap;

import java.util.Map;
import java.util.UUID;

public class InMemoryPojoDataAccessWriterImpl
        extends InMemoryPojoDataAccessWriter<UUID, PersonData, PersonVersionData> {

    public InMemoryPojoDataAccessWriterImpl(
            final Map<UUID, Archivable<PersonData>> objectDatas,
            final Multimap<UUID, TimeStamped<PersonVersionData>> versionObjectDatas) {
        super(objectDatas, versionObjectDatas);
    }
}
