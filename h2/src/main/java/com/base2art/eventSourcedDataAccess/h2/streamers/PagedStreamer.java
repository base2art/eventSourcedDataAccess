package com.base2art.eventSourcedDataAccess.h2.streamers;

import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
import com.base2art.eventSourcedDataAccess.EntityProducer;
import com.base2art.eventSourcedDataAccess.EnumSortParser;
import com.base2art.eventSourcedDataAccess.h2.DataProducer;
import com.base2art.eventSourcedDataAccess.h2.H2Connector;
import com.base2art.eventSourcedDataAccess.h2.Sql;
import com.base2art.eventSourcedDataAccess.h2.filters.H2ClauseCollection;
import lombok.val;

import java.util.stream.Stream;

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

        final String sortField = sortInformation.getFieldName();

        final H2ClauseCollection objectJoiner = new H2ClauseCollection();
        final H2ClauseCollection versionJoiner = new H2ClauseCollection();
        String sql = Sql.paged(getConnector(), marker, sortField, sortInformation.isAscending(), objectJoiner, versionJoiner);

        return getObjectEntityStream(marker, pageSize, objectJoiner, versionJoiner, sql);
    }
}