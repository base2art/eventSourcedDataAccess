package com.base2art.eventSourcedDataAccess.git.impls;

import com.base2art.eventSourcedDataAccess.git.GitContainer;
import com.base2art.eventSourcedDataAccess.git.GitDataAccessConfiguration;
import com.base2art.eventSourcedDataAccess.git.GitMessageQueue;
import com.base2art.eventSourcedDataAccess.git.GitPojoDataAccessWriter;
import com.base2art.eventSourcedDataAccess.git.GitWriter;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonData;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonVersionData;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

public class GitPojoPojoDataAccessWriterImpl
        extends GitPojoDataAccessWriter<UUID, PersonData, PersonVersionData> {

    public GitPojoPojoDataAccessWriterImpl(
            GitDataAccessConfiguration config,
            GitMessageQueue messageQueue,
            String catalogType) {

        super(new GitWriter<>(new GitContainer<>(config, messageQueue, catalogType, Object::toString), new ObjectMapper()));
    }
}
