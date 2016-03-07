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

    public Stream<ObjectEntity> get(final OrderOptions orderOptions, final Id marker, final int pageSize)
            throws DataAccessReaderException {

        String sql = "SELECT * FROM " + this.getConnector().objectTable();//+
//                     " WHERE ";

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