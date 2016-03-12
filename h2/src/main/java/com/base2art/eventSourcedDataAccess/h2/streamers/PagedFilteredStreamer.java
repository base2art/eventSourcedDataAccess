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

import java.util.Map;
import java.util.stream.Stream;

import static com.base2art.eventSourcedDataAccess.h2.H2Queries.fetchObjectMap;

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

        final String sortField = sortInformation.getFieldName();

        val objectJoiner = new H2ClauseCollection();
        SqlBuilder.process(filterOptions, "ot", objectFields, objectJoiner);

        val versionJoiner = new H2ClauseCollection();
        SqlBuilder.process(filterOptions, "p1", this.getConnector().nonFinalObjectVersionDataFields(), versionJoiner);

        String sql = Sql.paged(getConnector(), marker, objectFields, sortField, sortInformation.isAscending(), objectJoiner, versionJoiner);

        if (marker != null) {
            System.out.println(sql);
        }

        Map<Id, ObjectData> objectDatas = fetchObjectMap(
                this.getConnector(),
                sql,
                (statement) -> {
                    if (marker == null) {
                        int counter = versionJoiner.setParameters(statement, 1);
                        counter = objectJoiner.setParameters(statement, counter);
                        statement.setInt(counter, pageSize);
                    }
                    else {

                        int counter = versionJoiner.setParameters(statement, 1);
                        counter = objectJoiner.setParameters(statement, counter);
                        counter = versionJoiner.setParameters(statement, counter);
                        counter = objectJoiner.setParameters(statement, counter);
                        getConnector().idH2Type().setParameter(statement, counter, marker);
                        statement.setInt(counter + 1, pageSize);
                    }
                },
                objectFields,
                this.producer()::createObjectData);
        return fetchAndMapVersionToEntity(objectDatas);
    }
}