package com.base2art.eventSourcedDataAccess.h2;

public interface DataProducer<Id, ObjectData, VersionObjectData> {

    VersionObjectData createVersionObjectData(final Id id);

    ObjectData createObjectData(final Id id);
}
