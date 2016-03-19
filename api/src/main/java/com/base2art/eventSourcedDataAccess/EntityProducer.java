package com.base2art.eventSourcedDataAccess;

public interface EntityProducer<Id, ObjectEntity, ObjectData, VersionObjectData> {
    ObjectEntity getObjectEntity(
            Id id,
            ObjectData objectData,
            VersionObjectData versionObjectData);
}
