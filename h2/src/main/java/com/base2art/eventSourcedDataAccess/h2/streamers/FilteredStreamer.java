package com.base2art.eventSourcedDataAccess.h2.streamers;

import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
import com.base2art.eventSourcedDataAccess.EntityProducer;
import com.base2art.eventSourcedDataAccess.h2.DataProducer;
import com.base2art.eventSourcedDataAccess.h2.H2Connector;
import com.base2art.eventSourcedDataAccess.h2.Sql;
import com.base2art.eventSourcedDataAccess.h2.filters.H2ClauseCollection;
import com.base2art.eventSourcedDataAccess.h2.utils.SqlBuilder;
import lombok.val;

import java.util.Map;
import java.util.stream.Stream;

import static com.base2art.eventSourcedDataAccess.h2.H2Queries.fetchObjectMap;

public class FilteredStreamer<Id, ObjectEntity, ObjectData, VersionObjectData, FilterOptions>
        extends StreamerBase<Id, ObjectEntity, ObjectData, VersionObjectData> {

    public FilteredStreamer(
            final H2Connector<Id> connector,
            final EntityProducer<Id, ObjectEntity, ObjectData, VersionObjectData> entityProducer,
            final DataProducer<Id, ObjectData, VersionObjectData> producer) {
        super(connector, entityProducer, producer);
    }

    public Stream<ObjectEntity> get(final FilterOptions filterOptions)
            throws DataAccessReaderException {

        val versionJoiner = new H2ClauseCollection();
        SqlBuilder.process(filterOptions, "p2", this.getConnector().nonFinalObjectVersionDataFields(), versionJoiner);
        val objectJoiner = new H2ClauseCollection();
        SqlBuilder.process(filterOptions, "oq", this.getConnector().nonFinalObjectDataFields(), objectJoiner);

        String sql = Sql.filtered(this.getConnector(), objectJoiner, versionJoiner);

//        System.out.println(sql);

        Map<Id, ObjectData> objectDatas = fetchObjectMap(
                this.getConnector(),
                sql,
                (statement) -> {
                    int counter = versionJoiner.setParameters(statement, 1);
                    objectJoiner.setParameters(statement, counter);
                },
                this.getConnector().nonFinalObjectDataFields(),
                this.producer()::createObjectData);
        return fetchAndMapVersionToEntity(objectDatas);
    }
}