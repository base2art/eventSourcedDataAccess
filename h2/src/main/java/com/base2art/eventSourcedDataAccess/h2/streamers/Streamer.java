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

public class Streamer<Id, ObjectEntity, ObjectData, VersionObjectData>
        extends StreamerBase<Id, ObjectEntity, ObjectData, VersionObjectData> {


    public Streamer(
            final H2Connector<Id> connector,
            final EntityProducer<Id, ObjectEntity, ObjectData, VersionObjectData> entityProducer,
            final DataProducer<Id, ObjectData, VersionObjectData> producer) {

        super(connector, entityProducer, producer);
    }

    public Stream<ObjectEntity> get() throws DataAccessReaderException {


        val objectJoiner = new H2ClauseCollection();
        val versionJoiner = new H2ClauseCollection();

        String sql = Sql.filtered(this.getConnector(), objectJoiner, versionJoiner);

        return getObjectEntityStream(null, Integer.MAX_VALUE, objectJoiner, versionJoiner, sql);
    }
}
