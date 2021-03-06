package com.base2art.eventSourcedDataAccess.git.impls;

import com.base2art.eventSourcedDataAccess.git.GitContainer;
import com.base2art.eventSourcedDataAccess.git.GitDataAccessConfiguration;
import com.base2art.eventSourcedDataAccess.git.GitPojoDataAccessWriter;
import com.base2art.eventSourcedDataAccess.git.GitWriter;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonData;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonVersionData;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

public class GitPojoPojoDataAccessWriterImpl
        extends GitPojoDataAccessWriter<UUID, PersonData, PersonVersionData> {

    public GitPojoPojoDataAccessWriterImpl(
            GitDataAccessConfiguration config, String catalogType) {

        super(new GitWriter<>(new GitContainer<>(config, catalogType, Object::toString), new ObjectMapper(), false));
    }
}
