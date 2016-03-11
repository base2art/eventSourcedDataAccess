package com.base2art.eventSourcedDataAccess.h2.streamers;

import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
import com.base2art.eventSourcedDataAccess.EntityProducer;
import com.base2art.eventSourcedDataAccess.h2.DataProducer;
import com.base2art.eventSourcedDataAccess.h2.H2Connector;
import com.base2art.eventSourcedDataAccess.h2.parameters.RawH2Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Stream;

import static com.base2art.eventSourcedDataAccess.h2.H2Queries.fetchObjectMap;
import static com.base2art.eventSourcedDataAccess.utils.Arrays.createGenericArray;

public class StreamerBase<Id, ObjectEntity, ObjectData, VersionObjectData> {

    private final H2Connector<Id> connector;
    private final DataProducer<Id, ObjectData, VersionObjectData> producer;
    private final EntityProducer<Id, ObjectEntity, ObjectData, VersionObjectData> entityProducer;

    public StreamerBase(
            final H2Connector<Id> connector,
            final EntityProducer<Id, ObjectEntity, ObjectData, VersionObjectData> entityProducer,
            final DataProducer<Id, ObjectData, VersionObjectData> producer) {
        this.connector = connector;
        this.producer = producer;
        this.entityProducer = entityProducer;
    }

    public H2Connector<Id> getConnector() {
        return connector;
    }

    public DataProducer<Id, ObjectData, VersionObjectData> producer() {
        return producer;
    }

    public EntityProducer<Id, ObjectEntity, ObjectData, VersionObjectData> getEntityProducer() {
        return entityProducer;
    }

    protected Stream<ObjectEntity> fetchAndMapVersionToEntity(final Map<Id, ObjectData> objectDatas) throws DataAccessReaderException {
        Id[] ids = objectDatas.keySet()
                              .toArray(createGenericArray(this.connector.idType(), 0));

        StringJoiner joiner = new StringJoiner(", ", "(", ")");
        for (int i = 0; i < ids.length; i++) {
            joiner.add("?");
        }

        String sqlVersion = "SELECT p1.*\n" +
                            "FROM " + this.connector.objectVersionTable() + " p1" +
                            "  LEFT JOIN " + this.connector.objectVersionTable() + " p2" +
                            "     ON (p1.object_id = p2.object_id) AND (p1.OBJECT_VERSION_ID < p2.OBJECT_VERSION_ID)\n" +
                            "WHERE p2.object_version_id IS NULL AND p1.object_id in " +
                            joiner.toString();

        RawH2Type type = this.connector.idH2Type();
        Map<Id, VersionObjectData> versionObjectDatas = fetchObjectMap(
                connector,
                sqlVersion,
                (stmt) -> {
                    for (int i = 0; i < ids.length; i++) {
                        type.setParameter(stmt, 1 + i, ids[i]);
                    }
                },
                this.connector.nonFinalObjectVersionDataFields(),
                this.producer()::createVersionObjectData);

        List<ObjectEntity> items = new ArrayList<>();
        for (Map.Entry<Id, ObjectData> pair : objectDatas.entrySet()) {
            items.add(this.entityProducer.getObjectEntity(
                    pair.getKey(),
                    Optional.ofNullable(pair.getValue()),
                    Optional.ofNullable(versionObjectDatas.getOrDefault(pair.getKey(), null))));
        }

        return items.stream();
    }
}
