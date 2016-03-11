//package com.base2art.eventSourcedDataAccess.h2.streamers;
//
//import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
//import com.base2art.eventSourcedDataAccess.EntityProducer;
//import com.base2art.eventSourcedDataAccess.h2.DataProducer;
//import com.base2art.eventSourcedDataAccess.h2.H2Connector;
//import com.base2art.eventSourcedDataAccess.h2.filters.H2ClauseCollection;
//import com.base2art.eventSourcedDataAccess.h2.utils.SqlBuilder;
//import lombok.val;
//
//import java.util.Map;
//import java.util.stream.Stream;
//
//import static com.base2art.eventSourcedDataAccess.h2.H2Queries.fetchObjectMap;
//
//public class PagedFilteredStreamer<Id, ObjectEntity, ObjectData, VersionObjectData, FilterOptions, OrderOptions>
//        extends StreamerBase<Id, ObjectEntity, ObjectData, VersionObjectData> {
//
//    public PagedFilteredStreamer(
//            final H2Connector<Id> connector,
//            final EntityProducer<Id, ObjectEntity, ObjectData, VersionObjectData> entityProducer,
//            final DataProducer<Id, ObjectData, VersionObjectData> producer) {
//        super(connector, entityProducer, producer);
//    }
//
//    public Stream<ObjectEntity> get(final FilterOptions filterOptions)
//            throws DataAccessReaderException {
//
//        val versionJoiner = new H2ClauseCollection();
//        SqlBuilder.process(filterOptions, "p2", this.getConnector().nonFinalObjectVersionDataFields(), versionJoiner);
//        val objectJoiner = new H2ClauseCollection();
//        SqlBuilder.process(filterOptions, "oq", this.getConnector().nonFinalObjectDataFields(), objectJoiner);
//
//        final String versionClause = versionJoiner.length() == 0 ? "" : " AND " + versionJoiner.join(" AND ");
//        final String objectClause = objectJoiner.length() == 0 ? "" : " AND " + objectJoiner.join(" AND ");
//        final String sqlVersion = "SELECT p1.object_id" +
//                                  "  FROM " + this.getConnector().objectVersionTable() + " p1" +
//                                  "    LEFT JOIN " + this.getConnector().objectVersionTable() + " p2" +
//                                  "      ON (p1.object_id = p2.object_id) AND (p1.OBJECT_VERSION_ID < p2.OBJECT_VERSION_ID)" +
//                                  "  WHERE p2.object_version_id IS NULL" +
//                                  versionClause;
//        String sql = "SELECT * FROM " + this.getConnector().objectTable() + " oq" +
//                     "  WHERE object_id in (" + sqlVersion + ")" +
//                     objectClause;
//
//        System.out.println(sql);
//
//        Map<Id, ObjectData> objectDatas = fetchObjectMap(
//                this.getConnector(),
//                sql,
//                (statement) -> {
//                    int counter = versionJoiner.setParameters(statement, 1);
//                    objectJoiner.setParameters(statement, counter);
//                },
//                this.getConnector().nonFinalObjectDataFields(),
//                this.producer()::createObjectData);
//        return fetchAndMapVersionToEntity(objectDatas);
//    }
//}