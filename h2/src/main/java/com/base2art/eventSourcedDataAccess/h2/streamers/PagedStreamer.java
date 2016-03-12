package com.base2art.eventSourcedDataAccess.h2.streamers;

import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
import com.base2art.eventSourcedDataAccess.EntityProducer;
import com.base2art.eventSourcedDataAccess.EnumSortParser;
import com.base2art.eventSourcedDataAccess.h2.DataProducer;
import com.base2art.eventSourcedDataAccess.h2.H2Connector;
import com.base2art.eventSourcedDataAccess.h2.Sql;
import lombok.val;

import java.util.Map;
import java.util.stream.Stream;

import static com.base2art.eventSourcedDataAccess.h2.H2Queries.fetchObjectMap;

public class PagedStreamer<Id, ObjectEntity, ObjectData, VersionObjectData, OrderOptions>
        extends StreamerBase<Id, ObjectEntity, ObjectData, VersionObjectData> {

    public PagedStreamer(
            final H2Connector<Id> connector,
            final EntityProducer<Id, ObjectEntity, ObjectData, VersionObjectData> entityProducer,
            final DataProducer<Id, ObjectData, VersionObjectData> producer) {
        super(connector, entityProducer, producer);
    }

    public Stream<ObjectEntity> get(
            final OrderOptions options,
            final Id marker,
            final int pageSize)
            throws DataAccessReaderException {

        val sortInformation = EnumSortParser.parse((Enum) options);

        val objectFields = this.getConnector().nonFinalObjectDataFields();

        final String sortField = sortInformation.getFieldName();

        String sql = Sql.paged(getConnector(), marker, objectFields, sortField, sortInformation.isAscending(), null, null);

        Map<Id, ObjectData> objectDatas = fetchObjectMap(
                this.getConnector(),
                sql,
                (statement) -> {
                    if (marker == null) {
                        statement.setInt(1, pageSize);
                    }
                    else {

                        getConnector().idH2Type().setParameter(statement, 1, marker);
                        statement.setInt(2, pageSize);
                    }
                },
                objectFields,
                this.producer()::createObjectData);
        return fetchAndMapVersionToEntity(objectDatas);
    }
}