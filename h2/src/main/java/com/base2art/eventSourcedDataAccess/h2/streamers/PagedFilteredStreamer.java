package com.base2art.eventSourcedDataAccess.h2.streamers;

import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
import com.base2art.eventSourcedDataAccess.EntityProducer;
import com.base2art.eventSourcedDataAccess.EnumSortParser;
import com.base2art.eventSourcedDataAccess.h2.DataProducer;
import com.base2art.eventSourcedDataAccess.h2.H2Connector;
import com.base2art.eventSourcedDataAccess.h2.Sql;
import com.base2art.eventSourcedDataAccess.h2.filters.H2ClauseCollection;
import com.base2art.eventSourcedDataAccess.h2.utils.SqlBuilder;
import lombok.val;

import java.util.stream.Stream;

public class PagedFilteredStreamer<Id, ObjectEntity, ObjectData, VersionObjectData, FilterOptions, OrderOptions>
        extends StreamerBase<Id, ObjectEntity, ObjectData, VersionObjectData> {

    public PagedFilteredStreamer(
            final H2Connector<Id> connector,
            final EntityProducer<Id, ObjectEntity, ObjectData, VersionObjectData> entityProducer,
            final DataProducer<Id, ObjectData, VersionObjectData> producer) {
        super(connector, entityProducer, producer);
    }

    public Stream<ObjectEntity> get(
            final FilterOptions filterOptions,
            final OrderOptions orderOptions,
            final Id marker,
            final int pageSize)
            throws DataAccessReaderException {

        val sortInformation = EnumSortParser.parse((Enum) orderOptions);

        val objectFields = this.getConnector().nonFinalObjectDataFields();
        val versionFields = this.getConnector().nonFinalObjectVersionDataFields();

        final String sortField = sortInformation.getFieldName();

        val objectJoiner = new H2ClauseCollection();
        SqlBuilder.process(filterOptions, "ot", objectFields, objectJoiner);

        val versionJoiner = new H2ClauseCollection();
        SqlBuilder.process(filterOptions, "p1", versionFields, versionJoiner);

        String sql = Sql.paged(getConnector(), marker, sortField, sortInformation.isAscending(), objectJoiner, versionJoiner);

        return getObjectEntityStream(marker, pageSize, objectJoiner, versionJoiner, sql);
    }
}