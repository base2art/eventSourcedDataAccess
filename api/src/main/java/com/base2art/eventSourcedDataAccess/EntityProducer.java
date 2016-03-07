package com.base2art.eventSourcedDataAccess;

import java.util.Optional;

public interface EntityProducer<Id, ObjectEntity, ObjectData, VersionObjectData> {
    ObjectEntity getObjectEntity(
            Id id,
            Optional<ObjectData> objectData,
            Optional<VersionObjectData> versionObjectData);
}
