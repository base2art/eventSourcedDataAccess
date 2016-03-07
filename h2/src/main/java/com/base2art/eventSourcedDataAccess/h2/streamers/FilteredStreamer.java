package com.base2art.eventSourcedDataAccess.h2.streamers;

import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
import com.base2art.eventSourcedDataAccess.EntityProducer;
import com.base2art.eventSourcedDataAccess.h2.DataProducer;
import com.base2art.eventSourcedDataAccess.h2.H2Connector;
import com.base2art.eventSourcedDataAccess.h2.utils.SqlBuilder;
import lombok.val;

import java.util.Map;
import java.util.StringJoiner;
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

        val versionJoiner = new StringJoiner(" AND ");
        SqlBuilder.process(filterOptions, versionJoiner, this.getConnector().nonFinalObjectVersionDataFields());
        val objectJoiner = new StringJoiner(" AND ");
        SqlBuilder.process(filterOptions, objectJoiner, this.getConnector().nonFinalObjectDataFields());

        final String versionClause = versionJoiner.length() == 0 ? "" : " AND " + versionJoiner.toString();
        final String objectClause = objectJoiner.length() == 0 ? "" : " AND " + objectJoiner.toString();
        final String sqlVersion = "SELECT p1.object_id" +
                                  "  FROM " + this.getConnector().objectVersionTable() + " p1" +
                                  "    LEFT JOIN " + this.getConnector().objectVersionTable() + " p2" +
                                  "      ON (p1.object_id = p2.object_id) AND (p1.OBJECT_VERSION_ID < p2.OBJECT_VERSION_ID)" +
                                  "  WHERE p2.object_version_id IS NULL" +
                                  versionClause;
        String sql = "SELECT * FROM " + this.getConnector().objectTable() +
                     "  WHERE object_id in (" + sqlVersion + ")" +
                     objectClause;

        Map<Id, ObjectData> objectDatas = fetchObjectMap(
                this.getConnector(),
                sql,
                null,
                this.getConnector().nonFinalObjectDataFields(),
                this.producer()::createObjectData);
        return fetchAndMapVersionToEntity(objectDatas);
    }

//
//    protected Stream<ObjectEntity> filterEntities(Stream<ObjectEntity> stream, FilterOptions options) {
//
//        if (options == null) {
//            return stream;
//        }
//
//        return filterer.filter(stream, options);
//    }
}