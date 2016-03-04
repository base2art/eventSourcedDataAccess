package com.base2art.eventSourcedDataAccess.h2.impls;

import com.base2art.eventSourcedDataAccess.h2.H2Connector;
import com.base2art.eventSourcedDataAccess.h2.H2PojoDataAccessWriter;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonData;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonVersionData;

import java.util.UUID;

public class H2PojoDataAccessWriterImpl
        extends H2PojoDataAccessWriter<UUID, PersonData, PersonVersionData> {

    public H2PojoDataAccessWriterImpl(H2Connector<UUID> connector) {
        super(connector);
    }
}
