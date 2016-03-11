package com.base2art.eventSourcedDataAccess.h2.streamers;

import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
import com.base2art.eventSourcedDataAccess.EntityProducer;
import com.base2art.eventSourcedDataAccess.h2.DataProducer;
import com.base2art.eventSourcedDataAccess.h2.H2Connector;

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

    public Stream<ObjectEntity> get(final OrderOptions options, final Id marker, final int pageSize)
            throws DataAccessReaderException {

        final String sqlVersion = "SELECT p1.object_id" +
                                  "  FROM " + this.getConnector().objectVersionTable() + " p1" +
                                  "    LEFT JOIN " + this.getConnector().objectVersionTable() + " p2" +
                                  "      ON (p1.object_id = p2.object_id) AND (p1.OBJECT_VERSION_ID < p2.OBJECT_VERSION_ID)" +
                                  "  WHERE p2.object_version_id IS NULL";

        String sql = "SELECT oq.*, rownum() FROM " + this.getConnector().objectTable() + " oq" +
                     "  WHERE object_id in (" + sqlVersion + ")" +
                     "  LIMIT ? ";

        String orderedQuery =
                "  SELECT *" +
                "    FROM PERSONDATA_OBJECT po" +
                "    ORDER BY ? ?";

//        String sql = "\n" +
//                     "SELECT pj.* \n" +
//                     "  FROM (\n" +
//                     "        SELECT porn.Object_Id\n" +
//                     "          FROM (\n" +
//                     "                SELECT rownum() as num, poo.Object_id\n" +
//                     "                    FROM (\n" +
//                     "                           SELECT Object_Id\n" +
//                     "                               FROM PERSONDATA_OBJECT \n" +
//                     "                               ORDER BY ? ?\n" +
//                     "                          ) poo\n" +
//                     "               ) porn\n" +
//                     "          WHERE porn.num > 140\n" +
//                     "          LIMIT 10\n" +
//                     "       ) pooq\n" +
//                     "    \n" +
//                     "\n" +
//                     "  LEFT JOIN PERSONDATA_OBJECT pj \n" +
//                     "    ON pj.Object_id=pooq.Object_Id\n" +
//                     "  ORDER BY SocialSecurityNumber\n";

        System.out.println(sql);

        Map<Id, ObjectData> objectDatas = fetchObjectMap(
                this.getConnector(),
                sql,
                (statement) -> {
                    statement.setInt(1, 1);
                    statement.setInt(2, 1);
                    statement.setInt(3, 1);
                },
                this.getConnector().nonFinalObjectDataFields(),
                this.producer()::createObjectData);
        return fetchAndMapVersionToEntity(objectDatas);
    }
}